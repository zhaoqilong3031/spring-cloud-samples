package com.zhaoql.tcc.coordinator.domain;

import java.io.Serializable;
import java.util.List;

import com.zhaoql.support.tcc.Participant;

import lombok.Data;

/**
 * 
 * @author Administrator
 *
 */
@Data
public class TccCoordinatorRequest implements Serializable {

    private static final long serialVersionUID = 1847362921754190531L;
 
    private List<Participant> participants;

}
