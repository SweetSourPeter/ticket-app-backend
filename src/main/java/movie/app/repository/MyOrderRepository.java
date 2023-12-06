package movie.app.repository;

import movie.app.entity.MyOrderInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MyOrderRepository extends CrudRepository<MyOrderInfo, Long> {
    List<MyOrderInfo> findByAccount(String account);
    // You can add custom query methods if needed
}
