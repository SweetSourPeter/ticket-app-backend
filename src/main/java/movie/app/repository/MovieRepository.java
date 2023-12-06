package movie.app.repository;

import movie.app.entity.MovieInfo;
import movie.app.entity.MyOrderInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository  extends CrudRepository<MovieInfo, Long> {
    Optional<MovieInfo> findAllById(String movieId);
}
