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
public class Album {

    private int id;
    private String title;
    private int artist_Id;

}

