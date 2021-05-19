package com.anlohse.minesweeper.commons.services;

import com.anlohse.minesweeper.commons.entities.User;
import com.anlohse.minesweeper.commons.exceptions.InvalidRequestException;
import com.anlohse.minesweeper.commons.exceptions.ResourceNotFoundException;
import com.anlohse.minesweeper.commons.mapper.CurrentUserMapper;
import com.anlohse.minesweeper.commons.mapper.UserMapper;
import com.anlohse.minesweeper.commons.repositories.UserRepository;
import com.anlohse.minesweeper.commons.security.SHA256PasswordEncoder;
import com.anlohse.minesweeper.commons.util.IOUtils;
import com.anlohse.minesweeper.commons.util.Messages;
import com.anlohse.minesweeper.commons.vo.CurrentUserVO;
import com.anlohse.minesweeper.commons.vo.UserVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService  extends AbstractCrudService<User, Long> {

    @Value("${minesweeper.config.salty.activation}")
    private String activationSalty;

    @Value("${minesweeper.config.salty.recover}")
    private String recoverSalty;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Messages messages;

    @Autowired
    private SHA256PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }

    protected UserRepository getRepository() {
        return (UserRepository) this.repository;
    }

    public CurrentUserVO currentUserVo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            Long id = Long.parseLong(auth.getName());
            User user = repository.findById(id)
                    .orElseThrow(ResourceNotFoundException::new);
            return CurrentUserMapper.INSTANCE.userToVO(user);
        }
        return null;
    }

    public User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            Long id = Long.parseLong(auth.getName());
            return repository.findById(id)
                    .orElseThrow(ResourceNotFoundException::new);
        }
        return null;
    }

    public void createNew(UserVO userVo) throws Exception {
        User user = UserMapper.INSTANCE.voToUser(userVo);
        user.setPassword(passwordEncoder.encode(userVo.getPassword()));
        generateActivationCode(user);
        user = this.save(user);
        emailService.sendMailUser(UserMapper.INSTANCE.userToVO(user), messages.getString("user.activation.subject"), "activation");
    }

    private void generateActivationCode(User user) {
        String code = generateCode(user, activationSalty);
        user.setActivationExpires(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)));
        user.setActivationCode(code);
    }

    private String generateCode(User user, String salty) {
        MessageDigest sha256Digest = DigestUtils.getSha256Digest();
        sha256Digest.update(user.getEmail().getBytes());
        sha256Digest.update(salty.getBytes());
        sha256Digest.update(IOUtils.longToBytes(System.currentTimeMillis()));
        byte[] bs = sha256Digest.digest();
        return UUID.nameUUIDFromBytes(bs).toString();
    }

    public void update(UserVO userVo) {
        User user = repository.findById(userVo.getId())
                .orElseThrow(ResourceNotFoundException::new);
        user.setName(userVo.getName());
        user.setLastName(userVo.getLastName());
        user.setBirthDate(userVo.getBirthDate());
        repository.save(user);
    }

    public void resendActivationCode(String email) throws Exception {
        User user = getRepository().findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("E-Mail not found in the database."));
        generateActivationCode(user);
        repository.save(user);
        emailService.sendMailUser(UserMapper.INSTANCE.userToVO(user), messages.getString("user.activation.subject"), "activation");
    }

    public void activate(String activationCode) throws Exception {
        User user = getRepository().findByActivationCode(activationCode).orElseThrow(() -> new ResourceNotFoundException("Activation code not found."));
        if (user.getActivationExpires().before(Date.from(Instant.now()))) {
            throw new InvalidRequestException("user.activation.expired");
        }
        user.setActivationCode(null);
        repository.save(user);
        emailService.sendMailUser(UserMapper.INSTANCE.userToVO(user), messages.getString("user.activation_success.subject"), "activation_success");
    }

    private void generateRecoverCode(User user) {
        String code = generateCode(user, recoverSalty);
        user.setRecoveryExpires(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)));
        user.setRecoveryCode(code);
    }

    public void generateRecover(String email) throws Exception {
        User user = getRepository().findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("E-Mail not found in the database."));
        generateRecoverCode(user);
        repository.save(user);
        emailService.sendMailUser(UserMapper.INSTANCE.userToVO(user), messages.getString("user.recovery.subject"), "recovery");
    }

    public void recover(String recoverCode, String password, String passwordConfirmation) throws Exception {
        User user = getRepository().findByRecoveryCode(recoverCode).orElseThrow(() -> new ResourceNotFoundException("Recovery code not found."));
        if (!password.equals(passwordConfirmation)) {
            throw new InvalidRequestException("user.password.dont_match");
        }
        if (user.getRecoveryExpires().before(Date.from(Instant.now()))) {
            throw new InvalidRequestException("user.recover.expired");
        }
        user.setRecoveryCode(null);
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
        emailService.sendMailUser(UserMapper.INSTANCE.userToVO(user), messages.getString("user.recovery_success.subject"), "recovery_success");
    }

}
