package com.company.base.repository.model;

import static com.company.base.endpoint.rest.security.model.Role.WHISTLEBLOWER;

import com.company.base.endpoint.rest.security.model.Role;
import com.opencsv.bean.CsvBindByName;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Whistleblower {
  @CsvBindByName(column = "STDREF")
  private String studentRef;

  @CsvBindByName(column = "W_TOKEN")
  private String token;

  @CsvBindByName(column = "W_IP")
  private String ip;

  public Set<Role> getRoles() {
    return Set.of(WHISTLEBLOWER);
  }
}
