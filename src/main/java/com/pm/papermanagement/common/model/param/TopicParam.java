package com.pm.papermanagement.common.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicParam implements Serializable {
    String topic;
}
