package com.company.base.concurrency;

import static com.company.base.concurrency.ThreadRenamer.getRandomSubThreadNamePrefixFrom;
import static com.company.base.concurrency.ThreadRenamer.renameThread;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

import com.company.base.PojaGenerated;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@PojaGenerated
@Component
@Slf4j
public class Workers<T> {
  private final ExecutorService executorService;

  public Workers() {
    this.executorService = newVirtualThreadPerTaskExecutor();
  }

  @SneakyThrows
  public void invokeAll(List<Callable<T>> callables) {
    var parentThread = currentThread();
    callables = renameThreads(parentThread, callables);
    List<Future<T>> futures = executorService.invokeAll(callables);
    List<Throwable> exceptions = collectExceptions(futures);

    if (!exceptions.isEmpty()) {
      RuntimeException combinedException =
          new RuntimeException("Errors occurred during one callable or more:");
      exceptions.forEach(combinedException::addSuppressed);
      throw combinedException;
    }
  }

  public List<Callable<T>> renameThreads(Thread parentThread, List<Callable<T>> callables) {
    return callables.stream()
        .map(
            c ->
                (Callable<T>)
                    () -> {
                      renameThread(currentThread(), getRandomSubThreadNamePrefixFrom(parentThread));
                      return c.call();
                    })
        .toList();
  }

  public List<Throwable> collectExceptions(List<Future<T>> futures) {
    List<Throwable> exceptions = new ArrayList<>();
    futures.forEach(
        future -> {
          try {
            future.get();
          } catch (Exception e) {
            exceptions.add(e);
            log.error("Error occurred: {}", e.getMessage());
          }
        });
    return exceptions;
  }
}
