package io.renren.common.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class FftUtils {
    private Integer[] sourceData;
    private Double[] fftData;

    public static  FftUtils fromByte(byte[] data) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        Integer paramsLen = dis.readInt();
        Integer[] params = new Integer[paramsLen];
        for(Integer i = 0; i < paramsLen; i++){
            params[i] = dis.readInt();
        }

        Integer settingLen = dis.readInt();
        Integer[] settings = new Integer[settingLen];
        for(Integer j = 0; j < settingLen; j++){
            settings[j] = dis.readInt();
        }

        Integer DataLen  = dis.readInt();
        FftUtils tmp = new FftUtils();
        tmp.sourceData = new Integer[DataLen];
        for(int k = 0; k < DataLen; k++){
            tmp.sourceData[k] = dis.readInt();
        }

        dis.close();
        bis.close();

        return tmp;
    }
}
