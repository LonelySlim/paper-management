package com.pm.papermanagement.common.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *   "topic": "string",
 *   "desc": "string",
 *   "is_public": true
 * }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperLibraryParam implements Serializable {
    String topic;
    String desc;
    String is_public;

}
