package business;

import lombok.Getter;
import lombok.Setter;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Artist {


    private int id;
    private String name;

}

