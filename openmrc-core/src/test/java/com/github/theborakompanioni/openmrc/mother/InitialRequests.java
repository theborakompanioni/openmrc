package com.github.theborakompanioni.openmrc.mother;


import com.github.theborakompanioni.openmrc.mother.json.InitialRequestJsonMother;
import com.github.theborakompanioni.openmrc.mother.protobuf.InitialRequestProtobufMother;

public class InitialRequests {
    public static InitialRequestJsonMother json() {
        return new InitialRequestJsonMother();
    }

    public static InitialRequestProtobufMother protobuf() {
        return new InitialRequestProtobufMother();
    }
}
