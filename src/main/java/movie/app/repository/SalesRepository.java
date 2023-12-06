package movie.app.repository;

import movie.app.entity.SaleInfo;
import org.springframework.data.repository.CrudRepository;

public interface SalesRepository extends CrudRepository<SaleInfo, Long> {
    // You can add custom query methods if needed
}
