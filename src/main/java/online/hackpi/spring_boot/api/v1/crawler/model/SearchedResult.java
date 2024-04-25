package online.hackpi.spring_boot.api.v1.crawler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchedResult {
    private Set<String> imagesSet;
    private Set<String> videosSet;
}
