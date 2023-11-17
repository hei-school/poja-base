package com.company.base.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class WhistleblowerRepositoryTest {

  private final WhistleblowerRepository subject = new WhistleblowerRepository();

  @Test
  public void can_find_authenticated_user() {
    var token = "5c738c7d-a045-472d-80bf-16a58f19623a";

    var w = subject.findByToken(token);

    assertEquals("STD21024", w.getStudentRef());
  }

  @Test
  public void cannot_find_unauthenticated_user() {
    var token = "not-a-token";

    assertThrows(NoSuchElementException.class, () -> subject.findByToken(token));
  }
}
