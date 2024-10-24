package business;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Represents a playlist created by a user.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    /**
     *  unique identifier for the playlist.
     */
    private int playlistId;

    /**
     *  name of the playlist.
     */
    private String name;

    /**
     * Indicates if playlist is public or private.
     */
    private boolean isPublic;

    /**
     * The user ID of the playlist owner.
     */
    private int userId;

    /**
     * The list of songs included in the playlist.
     */
    private List<Song> songs;
}
