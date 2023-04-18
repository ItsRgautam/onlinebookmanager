package SpringBoot_JBDL_L1314.lecture13_14.service;

import SpringBoot_JBDL_L1314.lecture13_14.exceptions.UserException;
import SpringBoot_JBDL_L1314.lecture13_14.models.StatusCode;
import SpringBoot_JBDL_L1314.lecture13_14.models.UserInfo;
import SpringBoot_JBDL_L1314.lecture13_14.repository.UserRepository;
import SpringBoot_JBDL_L1314.lecture13_14.requests.CreateUserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * <p>
     *     This method is used to create / persist new user object
     * </p>
     * @param userRequestDto
     * @return - userInfo without Id
     */
    public UserInfo createUser(CreateUserRequestDto userRequestDto) {
        UserInfo userInfo = userRequestDto.toUser();
        Optional<List<UserInfo>> optionalUserInfo = userRepository.findByEmail(userInfo.getEmail());
        List<UserInfo> userInfos=optionalUserInfo.get();
        if(!userInfos.isEmpty()){
            throw new UserException(StatusCode.CHEGG_08);
        }

        userRepository.save(userInfo);
        return userInfo;
    }


    /**
     * 10000 of records
     *  --> (0,20) (21,40)
     *
     *     limit 20
     *     limit 21 40
     *
     * @return
     */
    public List<UserInfo> fetchAllUsers(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 20);
        return userRepository.findAll(pageable).getContent();
    }

    public Optional<UserInfo> fetchOneById(Integer id) {
        return userRepository.findById(id);
    }



}

