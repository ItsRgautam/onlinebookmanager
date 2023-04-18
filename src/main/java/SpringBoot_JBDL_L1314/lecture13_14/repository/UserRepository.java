package SpringBoot_JBDL_L1314.lecture13_14.repository;

import SpringBoot_JBDL_L1314.lecture13_14.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {


    Optional<List<UserInfo>> findByEmail(String email);
}
