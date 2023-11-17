package com.company.base.repository;

import com.company.base.repository.model.Whistleblower;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class WhistleblowerRepository {
  public Whistleblower findByToken(String token) {
    List<Whistleblower> allUsers;
    try (Reader reader =
        new InputStreamReader(
            Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream("whistleblowers.csv")))) {
      var cb = new CsvToBeanBuilder<Whistleblower>(reader).withType(Whistleblower.class).build();

      allUsers = cb.parse();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return allUsers.stream()
        .filter(user -> token.equals(user.getToken()))
        .findFirst()
        .orElseThrow();
  }
}
