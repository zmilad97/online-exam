package com.github.zmilad97.onlineExam.module;

import javax.persistence.Id;
import java.io.Serializable;

public class ScoresIdClass implements Serializable {

    private long userId;

    private long examId;

    private long questionId;
}
