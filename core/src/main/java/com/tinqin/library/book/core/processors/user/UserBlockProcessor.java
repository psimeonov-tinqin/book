package com.tinqin.library.book.core.processors.user;

import static com.tinqin.library.book.api.ValidationMessages.USER_IS_ALREADY_BLOCK;
import static com.tinqin.library.book.api.ValidationMessages.USER_IS_NOT_ADMIN;
import static com.tinqin.library.book.api.ValidationMessages.USER_IS_NOT_FOUND;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.user.BlockUser;
import com.tinqin.library.book.api.user.BlockUserInput;
import com.tinqin.library.book.api.user.BlockUserOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBlockProcessor implements BlockUser {

  private final UserRepository userRepository;
  private final ErrorHandler errorHandler;

  @Override
  public Either<OperationError, BlockUserOutput> process(BlockUserInput input) {
    return
        validateAdmin(UUID.fromString(input.getId()))
            .flatMap(adminId -> validateUnblockedUser(UUID.fromString(input.getId())))
            .flatMap(this::blockUserAndSave)
            .map(this::convertUserToBlockUserOutput)
            .toEither()
            .mapLeft(errorHandler::handle);
  }

  private Try<UUID> validateAdmin(UUID adminId) {
    return Try.of(() -> userRepository.findById(adminId)
        .filter(User::isAdmin)
        .map(User::getId)
        .orElseThrow(
            () -> new IllegalArgumentException(USER_IS_NOT_ADMIN)));
  }

  private Try<UUID> validateUnblockedUser(UUID userId) {
    return Try.of(() -> userRepository.findUnblockUserId(userId)
        .orElseThrow(
            () -> new IllegalStateException(USER_IS_ALREADY_BLOCK)));
  }

  private Try<User> blockUserAndSave(UUID userId) {
    return Try.of(() -> {
      User userToBlock = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException(USER_IS_NOT_FOUND));
      userToBlock.setBlocked(true);
      userRepository.save(userToBlock);
      return userToBlock;
    });
  }

  private BlockUserOutput convertUserToBlockUserOutput(User user) {
    user.setBlocked(true);
    userRepository.save(user);
    return BlockUserOutput
        .builder()
        .build();

  }
}
