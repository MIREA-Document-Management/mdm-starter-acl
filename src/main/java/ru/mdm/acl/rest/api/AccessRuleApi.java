package ru.mdm.acl.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mdm.acl.model.dto.AccessRuleDto;
import ru.mdm.acl.model.dto.CreateAccessRuleDto;
import ru.mdm.acl.model.dto.UpdateAccessRuleDto;

import java.util.UUID;

/**
 * REST-API для работы с правилами доступа.
 */
public interface AccessRuleApi {

    String BASE_URI = "/api/v1/access-rules";

//    @Operation(summary = "Создать правило доступа",
//            description = "Создать новое правило доступа к объекту. Добавлять правило на объект может только его создатель",
//            responses = {
//                    @ApiResponse(responseCode = "201", description = "Правило успешно создано",
//                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                    schema = @Schema(implementation = AccessRuleDto.class))),
//                    @ApiResponse(responseCode = "400", description = "Неверный формат переданных значений",
//                            content = @Content(schema = @Schema(hidden = true))),
//                    @ApiResponse(responseCode = "403", description = "Операция запрещена",
//                            content = @Content(schema = @Schema(hidden = true))),
//                    @ApiResponse(responseCode = "404", description = "Объект не найден",
//                            content = @Content(schema = @Schema(hidden = true))),
//                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
//                            content = @Content(schema = @Schema(hidden = true)))
//            })
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    Mono<AccessRuleDto> createAccessRule(@RequestBody CreateAccessRuleDto dto);

    @Operation(summary = "Обновить правило доступа",
            description = "Обновить правило доступа к объекту. Обновлять правило может только создатель правила",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Правило успешно обновлено",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AccessRuleDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный формат переданных значений",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Операция запрещена",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Объект или правило не найдено",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                            content = @Content(schema = @Schema(hidden = true)))
            })
    @PutMapping("/{ruleId}")
    Mono<AccessRuleDto> updateAccessRule(
            @Parameter(description = "Идентфикатор правила", required = true)
            @PathVariable UUID ruleId,
            @RequestBody UpdateAccessRuleDto dto
    );

    @Operation(summary = "Получить список правил доступа объекта",
            description = "Получить список правил доступа объекта по заданным параметрам. Получить правила может только создатель объекта",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный запрос",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AccessRuleDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный формат переданных значений",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Операция запрещена",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Объект не найден",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                            content = @Content(schema = @Schema(hidden = true)))
            })
    @GetMapping
    Flux<AccessRuleDto> getObjectAccessRules(
            @Parameter(description = "Идентфикатор объекта", required = true)
            @RequestParam UUID objectId,
            Pageable pageable
    );

    @Operation(summary = "Получить правило",
            description = "Получить правило по идентификатору. Получить правило может только создатель правила",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный запрос",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AccessRuleDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный формат переданных значений",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Операция запрещена",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Правило не найдено",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                            content = @Content(schema = @Schema(hidden = true)))
            })
    @GetMapping("/{ruleId}")
    Mono<AccessRuleDto> getAccessRuleDto(
            @Parameter(description = "Идентфикатор правила", required = true)
            @PathVariable UUID ruleId
    );

    @Operation(summary = "Удалить правило",
            description = "Удалить правило по идентификатору. Удалить правило может только создатель правила",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Правило удалено",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AccessRuleDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный формат переданных значений",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Операция запрещена",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Правило не найдено",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                            content = @Content(schema = @Schema(hidden = true)))
            })
    @DeleteMapping("/{ruleId}")
    Mono<AccessRuleDto> deleteAccessRule(
            @Parameter(description = "Идентфикатор правила", required = true)
            @PathVariable UUID ruleId
    );

    @Operation(summary = "Удалить все правила объекта",
            description = "Удалить все правила по идентификатору документа. Удалять правила может только создатель объекта",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Правила удалены",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AccessRuleDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный формат переданных значений",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Операция запрещена",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Объект не найден",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                            content = @Content(schema = @Schema(hidden = true)))
            })
    @DeleteMapping
    Flux<AccessRuleDto> deleteAccessRules(
            @Parameter(description = "Идентфикатор объекта", required = true)
            @RequestParam UUID objectId
    );
}
