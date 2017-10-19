package spring.di.demo.domain;

import lombok.*;

@Getter
@Setter()
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
@ToString
public class Employee {

    private Long id;

    private String firstname;
    private String initials;
    private String surname;

    private Boolean fired;
}
