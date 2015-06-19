package org.tbk.openmrc.mother;

import org.tbk.openmrc.mother.json.InitialRequestJsonMother;
import org.tbk.openmrc.mother.protobuf.InitialRequestProtobufMother;

/**
 * Created by void on 19.06.15.
 */
public class InitialRequests {
    public static InitialRequestJsonMother json() {
        return new InitialRequestJsonMother();
    }

    public static InitialRequestProtobufMother protobuf() {
        return new InitialRequestProtobufMother();
    }
}
