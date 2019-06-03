package io.renren.common.utils;

import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import domainchange.CDomainChange;
import io.renren.modules.inspection.entity.FftChartEntity;
import org.apache.commons.lang.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class FftUtils {

    public static  FftChartEntity accFromByte(byte[] data) throws IOException {
        FftChartEntity out = new FftChartEntity();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        Integer paramsLen = dis.readInt();
        Float[] params = new Float[paramsLen];
        for(Integer i = 0; i < paramsLen; i++){
            params[i] = dis.readFloat();
        }
        if(params.length == 2){
            //out.ratio = params[0];
            out.setOffset(params[1].doubleValue());
        }

        Integer settingLen = dis.readInt();
        Integer[] settings = new Integer[settingLen];
        for(Integer j = 0; j < settingLen; j++){
            settings[j] = dis.readInt();
        }
        if(settings.length == 5){
            out.setCmd(settings[0]);
            out.setAxles(settings[1]);
            out.setChannel(settings[2]);
            out.setN(settings[3]);
            out.setFs(settings[4].doubleValue());
        }

        Integer DataLen  = dis.readInt();
        Integer[] sourceData = new Integer[DataLen];
        for(int k = 0; k < DataLen; k++){
            sourceData[k] = dis.readInt();
        }

        MWNumericArray t = null;
        MWNumericArray k = null;
        MWNumericArray pk_array = null;
        MWNumericArray pk2pk_array = null;
        MWNumericArray rm_array = null;
        MWNumericArray ff_first_array = null;
        MWNumericArray freq_first_array = null;
        MWNumericArray ff_second_array = null;
        MWNumericArray freq_second_array = null;
        MWNumericArray ff_third_array = null;
        MWNumericArray freq_third_array = null;
        MWNumericArray ff_array = null;
        MWNumericArray freq_array = null;

        Object[] res_adjust = null;
        Object[] res_time = null;
        Object[] res_fft = null;
        CDomainChange domainChange = null;
        try{
            domainChange = new CDomainChange();
            res_adjust = domainChange.to_adjust(2,sourceData,out.getRatio());
            t = (MWNumericArray) res_adjust[0];
            out.setTimeXData(ArrayUtils.toObject(t.getIntData()));
            k = (MWNumericArray) res_adjust[1];
            out.setTimeYData(ArrayUtils.toObject(k.getDoubleData()));
            k.getDoubleData();
            res_time = domainChange.time_domain(3,k);
            pk_array = (MWNumericArray)res_time[0];
            out.setPk(pk_array.getDouble());
            pk2pk_array = (MWNumericArray)res_time[1];
            out.setPk2pk(pk2pk_array.getDouble());
            rm_array = (MWNumericArray)res_time[2];
            out.setRm(rm_array.getDouble());

            res_fft = domainChange.fft_domain(8,out.getFs(),k);
            ff_first_array = (MWNumericArray)res_fft[0];
            out.setFf_first(ff_first_array.getDouble());
            freq_first_array = (MWNumericArray)res_fft[1];
            out.setFreq_first(freq_first_array.getDouble());
            ff_second_array = (MWNumericArray)res_fft[2];
            out.setFf_second(ff_second_array.getDouble());
            freq_second_array = (MWNumericArray)res_fft[3];
            out.setFreq_second(freq_second_array.getDouble());
            ff_third_array = (MWNumericArray)res_fft[4];
            out.setFf_third(ff_third_array.getDouble());
            freq_third_array = (MWNumericArray)res_fft[5];
            out.setFreq_third(freq_third_array.getDouble());
            ff_array = (MWNumericArray)res_fft[6];
            freq_array = (MWNumericArray)res_fft[7];
            out.setFftYData(ArrayUtils.toObject(ff_array.getDoubleData()));
            out.setFftXData(ArrayUtils.toObject(freq_array.getDoubleData()));
        }catch(MWException e){
            e.printStackTrace();
        }finally{
            MWArray.disposeArray(t);
            MWArray.disposeArray(k);
            MWArray.disposeArray(pk_array);
            MWArray.disposeArray(pk2pk_array);
            MWArray.disposeArray(rm_array);
            MWArray.disposeArray(ff_first_array);
            MWArray.disposeArray(freq_first_array);
            MWArray.disposeArray(ff_second_array);
            MWArray.disposeArray(freq_second_array);
            MWArray.disposeArray(ff_third_array);
            MWArray.disposeArray(freq_third_array);
            MWArray.disposeArray(res_adjust);
            MWArray.disposeArray(res_time);
            MWArray.disposeArray(res_fft);
            domainChange.dispose();
        }

        dis.close();
        bis.close();

        return out;
    }


    public static  FftChartEntity speedFromByte(byte[] data) throws IOException {
        FftChartEntity out = new FftChartEntity();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        Integer paramsLen = dis.readInt();
        Float[] params = new Float[paramsLen];
        for(Integer i = 0; i < paramsLen; i++){
            params[i] = dis.readFloat();
        }
        if(params.length == 2){
            //out.ratio = params[0];
            out.setOffset(params[1].doubleValue());
        }

        Integer settingLen = dis.readInt();
        Integer[] settings = new Integer[settingLen];
        for(Integer j = 0; j < settingLen; j++){
            settings[j] = dis.readInt();
        }
        if(settings.length == 5){
            out.setCmd(settings[0]);
            out.setAxles(settings[1]);
            out.setChannel(settings[2]);
            out.setN(settings[3]);
            out.setFs(settings[4].doubleValue());
        }

        Integer DataLen  = dis.readInt();
        Integer[] sourceData = new Integer[DataLen];
        for(int k = 0; k < DataLen; k++){
            sourceData[k] = dis.readInt();
        }

        MWNumericArray t = null;
        MWNumericArray k = null;
        MWNumericArray speed = null;
        MWNumericArray pk_array = null;
        MWNumericArray pk2pk_array = null;
        MWNumericArray rm_array = null;
        MWNumericArray ff_first_array = null;
        MWNumericArray freq_first_array = null;
        MWNumericArray ff_second_array = null;
        MWNumericArray freq_second_array = null;
        MWNumericArray ff_third_array = null;
        MWNumericArray freq_third_array = null;
        MWNumericArray ff_array = null;
        MWNumericArray freq_array = null;

        Object[] res_adjust = null;
        Object[] res_time = null;
        Object[] res_speed = null;
        Object[] res_fft = null;
        CDomainChange domainChange = null;
        try{
            domainChange = new CDomainChange();
            res_adjust = domainChange.to_adjust(2,sourceData,out.getRatio());
            t = (MWNumericArray) res_adjust[0];
            out.setTimeXData(ArrayUtils.toObject(t.getIntData()));
            k = (MWNumericArray) res_adjust[1];
            out.setTimeYData(ArrayUtils.toObject(k.getDoubleData()));
            k.getDoubleData();
            res_time = domainChange.time_domain(3,k);
            pk_array = (MWNumericArray)res_time[0];
            out.setPk(pk_array.getDouble());
            pk2pk_array = (MWNumericArray)res_time[1];
            out.setPk2pk(pk2pk_array.getDouble());
            rm_array = (MWNumericArray)res_time[2];
            out.setRm(rm_array.getDouble());

            res_speed = domainChange.to_integral(1,out.getFs(),k);
            speed = (MWNumericArray) res_speed[0];

            res_fft = domainChange.fft_domain(8,out.getFs(),speed);
            ff_first_array = (MWNumericArray)res_fft[0];
            out.setFf_first(ff_first_array.getDouble());
            freq_first_array = (MWNumericArray)res_fft[1];
            out.setFreq_first(freq_first_array.getDouble());
            ff_second_array = (MWNumericArray)res_fft[2];
            out.setFf_second(ff_second_array.getDouble());
            freq_second_array = (MWNumericArray)res_fft[3];
            out.setFreq_second(freq_second_array.getDouble());
            ff_third_array = (MWNumericArray)res_fft[4];
            out.setFf_third(ff_third_array.getDouble());
            freq_third_array = (MWNumericArray)res_fft[5];
            out.setFreq_third(freq_third_array.getDouble());
            ff_array = (MWNumericArray)res_fft[6];
            freq_array = (MWNumericArray)res_fft[7];
            out.setFftYData(ArrayUtils.toObject(ff_array.getDoubleData()));
            out.setFftXData(ArrayUtils.toObject(freq_array.getDoubleData()));
        }catch(MWException e){
            e.printStackTrace();
        }finally{
            MWArray.disposeArray(t);
            MWArray.disposeArray(k);
            MWArray.disposeArray(pk_array);
            MWArray.disposeArray(pk2pk_array);
            MWArray.disposeArray(rm_array);
            MWArray.disposeArray(ff_first_array);
            MWArray.disposeArray(freq_first_array);
            MWArray.disposeArray(ff_second_array);
            MWArray.disposeArray(freq_second_array);
            MWArray.disposeArray(ff_third_array);
            MWArray.disposeArray(freq_third_array);
            MWArray.disposeArray(res_adjust);
            MWArray.disposeArray(res_time);
            MWArray.disposeArray(res_fft);
            domainChange.dispose();
        }

        dis.close();
        bis.close();

        return out;
    }



    public static  FftChartEntity distanceFromByte(byte[] data) throws IOException {
        FftChartEntity out = new FftChartEntity();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        Integer paramsLen = dis.readInt();
        Float[] params = new Float[paramsLen];
        for(Integer i = 0; i < paramsLen; i++){
            params[i] = dis.readFloat();
        }
        if(params.length == 2){
            //out.ratio = params[0];
            out.setOffset(params[1].doubleValue());
        }

        Integer settingLen = dis.readInt();
        Integer[] settings = new Integer[settingLen];
        for(Integer j = 0; j < settingLen; j++){
            settings[j] = dis.readInt();
        }
        if(settings.length == 5){
            out.setCmd(settings[0]);
            out.setAxles(settings[1]);
            out.setChannel(settings[2]);
            out.setN(settings[3]);
            out.setFs(settings[4].doubleValue());
        }

        Integer DataLen  = dis.readInt();
        Integer[] sourceData = new Integer[DataLen];
        for(int k = 0; k < DataLen; k++){
            sourceData[k] = dis.readInt();
        }

        MWNumericArray t = null;
        MWNumericArray k = null;
        MWNumericArray speed = null;
        MWNumericArray distance = null;
        MWNumericArray pk_array = null;
        MWNumericArray pk2pk_array = null;
        MWNumericArray rm_array = null;
        MWNumericArray ff_first_array = null;
        MWNumericArray freq_first_array = null;
        MWNumericArray ff_second_array = null;
        MWNumericArray freq_second_array = null;
        MWNumericArray ff_third_array = null;
        MWNumericArray freq_third_array = null;
        MWNumericArray ff_array = null;
        MWNumericArray freq_array = null;

        Object[] res_adjust = null;
        Object[] res_speed = null;
        Object[] res_distance = null;
        Object[] res_time = null;
        Object[] res_fft = null;
        CDomainChange domainChange = null;
        try{
            domainChange = new CDomainChange();
            res_adjust = domainChange.to_adjust(2,sourceData,out.getRatio());
            t = (MWNumericArray) res_adjust[0];
            out.setTimeXData(ArrayUtils.toObject(t.getIntData()));
            k = (MWNumericArray) res_adjust[1];
            out.setTimeYData(ArrayUtils.toObject(k.getDoubleData()));
            k.getDoubleData();
            res_time = domainChange.time_domain(3,k);
            pk_array = (MWNumericArray)res_time[0];
            out.setPk(pk_array.getDouble());
            pk2pk_array = (MWNumericArray)res_time[1];
            out.setPk2pk(pk2pk_array.getDouble());
            rm_array = (MWNumericArray)res_time[2];
            out.setRm(rm_array.getDouble());

            res_speed = domainChange.to_integral(1,out.getFs(),k);
            speed = (MWNumericArray) res_speed[0];

            res_distance = domainChange.to_integral(1,out.getFs(),speed);
            distance = (MWNumericArray) res_distance[0];

            res_fft = domainChange.fft_domain(8,out.getFs(),distance);
            ff_first_array = (MWNumericArray)res_fft[0];
            out.setFf_first(ff_first_array.getDouble());
            freq_first_array = (MWNumericArray)res_fft[1];
            out.setFreq_first(freq_first_array.getDouble());
            ff_second_array = (MWNumericArray)res_fft[2];
            out.setFf_second(ff_second_array.getDouble());
            freq_second_array = (MWNumericArray)res_fft[3];
            out.setFreq_second(freq_second_array.getDouble());
            ff_third_array = (MWNumericArray)res_fft[4];
            out.setFf_third(ff_third_array.getDouble());
            freq_third_array = (MWNumericArray)res_fft[5];
            out.setFreq_third(freq_third_array.getDouble());
            ff_array = (MWNumericArray)res_fft[6];
            freq_array = (MWNumericArray)res_fft[7];
            out.setFftYData(ArrayUtils.toObject(ff_array.getDoubleData()));
            out.setFftXData(ArrayUtils.toObject(freq_array.getDoubleData()));
        }catch(MWException e){
            e.printStackTrace();
        }finally{
            MWArray.disposeArray(t);
            MWArray.disposeArray(k);
            MWArray.disposeArray(pk_array);
            MWArray.disposeArray(pk2pk_array);
            MWArray.disposeArray(rm_array);
            MWArray.disposeArray(ff_first_array);
            MWArray.disposeArray(freq_first_array);
            MWArray.disposeArray(ff_second_array);
            MWArray.disposeArray(freq_second_array);
            MWArray.disposeArray(ff_third_array);
            MWArray.disposeArray(freq_third_array);
            MWArray.disposeArray(res_adjust);
            MWArray.disposeArray(res_time);
            MWArray.disposeArray(res_fft);
            domainChange.dispose();
        }

        dis.close();
        bis.close();

        return out;
    }
}
