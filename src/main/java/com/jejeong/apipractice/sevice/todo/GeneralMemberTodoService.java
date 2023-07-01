package com.jejeong.apipractice.sevice.todo;

import com.jejeong.apipractice.controller.todo.request.DefaultTodoRequest;
import com.jejeong.apipractice.controller.todo.request.UpdateTodoRequest;
import com.jejeong.apipractice.controller.todo.response.DefaultTodoResponse;
import com.jejeong.apipractice.dto.todo.TodoDto;
import com.jejeong.apipractice.entity.todo.Todo;
import com.jejeong.apipractice.exception.errorCode.CommonErrorCode;
import com.jejeong.apipractice.exception.exception.ApplicationException;
import com.jejeong.apipractice.repository.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GeneralMemberTodoService implements TodoService {

  private final TodoRepository todoRepository;

  @Override
  public DefaultTodoResponse findTodo(Long todoId) {
    Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new ApplicationException(
        CommonErrorCode.TODO_NOT_FOUND, String.format("Todo not founded")));
    return DefaultTodoResponse.of(TodoDto.fromEntity(todo));
  }

  @Override
  public Page<DefaultTodoResponse> findTodoList(String userEmail, Pageable pageable) {
    // TODO 사용자 검증하기.

    return todoRepository.findAll(pageable).map(TodoDto::fromEntity).map(DefaultTodoResponse::of);
  }

  @Override
  @Transactional
  public void createTodo(DefaultTodoRequest req, String userEmail) {
    // TODO 사용자 정보 맵핑하기.

    Todo todo = Todo.of(req.getTitle(), req.getDescription(), req.getDueDate());
    todoRepository.save(todo);

  }

  @Override
  @Transactional
  public void modifyTodo(UpdateTodoRequest req, String userEmail) {
    Todo todo = todoRepository.findById(req.getId()).orElseThrow(
        () -> new ApplicationException(CommonErrorCode.TODO_NOT_FOUND,
            String.format("todo not exist %s", req.getId())));

    // TODO 사용자가 같은지 확인하기

    todo.updateTodo(req.getTitle(), req.getDescription(), req.getDueDate());
  }

  @Override
  @Transactional
  public void deleteTodo(Long todoId, String userEmail) {
    Todo todo = todoRepository.findById(todoId).orElseThrow(
        () -> new ApplicationException(CommonErrorCode.TODO_NOT_FOUND,
            String.format("todo not exist %s", todoId)));

    // TODO 사용자 확인하기

    todoRepository.delete(todo);

  }
}
