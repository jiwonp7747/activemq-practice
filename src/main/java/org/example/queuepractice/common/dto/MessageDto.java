package org.example.queuepractice.common.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MessageDto {

    private String type;
    private String message;
}
