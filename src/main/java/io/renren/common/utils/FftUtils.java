package io.renren.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.modules.inspection.entity.FftChartEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class FftUtils {

    private static String interfaceUrl;

    @Value("${interface.url}")
    public void setInterfaceUrl(String interfaceUrl) {
        this.interfaceUrl =  interfaceUrl;
    }

    public static FftChartEntity fromByte(byte[] data, String type) throws IOException {
        FftChartEntity out = new FftChartEntity();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        Integer paramsLen = dis.readInt();
        Float[] params = new Float[paramsLen];
        for (Integer i = 0; i < paramsLen; i++) {
            params[i] = dis.readFloat();
        }
        if (params.length == 2) {
            try{
                out.setRatio(params[0].doubleValue());
            }catch(Exception e){
            }
            try{
                out.setOffset(params[1].doubleValue());
            }catch(Exception e) {
            }
        }

        Integer settingLen = dis.readInt();
        Integer[] settings = new Integer[settingLen];
        for (Integer j = 0; j < settingLen; j++) {
            settings[j] = dis.readInt();
        }
        if (settings.length == 5) {
            out.setCmd(settings[0]);
            out.setAxles(settings[1]);
            out.setChannel(settings[2]);
            out.setN(settings[3]);
            out.setFs(settings[4].doubleValue());
        }

        Integer dataLen = dis.readInt();
        List<Double> sourceList = new ArrayList<>();
        for (int k = 0; k < dataLen; k++) {
            Integer tmp = dis.readInt();
            sourceList.add(tmp.doubleValue() / 130);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",sourceList);
        jsonObject.put("n",out.getN());
        jsonObject.put("fs",out.getFs());
        jsonObject.put("type",type);
        String reqUrl = interfaceUrl;
        String res = HttpUtils.post(reqUrl,jsonObject.toJSONString(),"");
        if(res == null){
            return null;
        }

        JSONObject jsonRes = JSONObject.parseObject(res);

        JSONArray jsonTimeYData = jsonRes.getJSONArray("timeYData");
        Double[] timeYData = new Double[jsonTimeYData.size()];
        for(int i = 0;i<jsonTimeYData.size();i++){
            timeYData[i] = jsonTimeYData.getDouble(i);
        }
        JSONArray jsonTimeXData = jsonRes.getJSONArray("timeXData");
        Double[] timeXData = new Double[jsonTimeXData.size()];
        for(int i = 0;i<jsonTimeXData.size();i++){
            timeXData[i] = jsonTimeXData.getDouble(i);
        }
        JSONArray jsonFftYData = jsonRes.getJSONArray("fftYData");
        Double[] fftYData = new Double[jsonFftYData.size()];
        for(int i = 0;i<jsonFftYData.size();i++){
            fftYData[i] = jsonFftYData.getDouble(i);
        }
        JSONArray jsonFftXData = jsonRes.getJSONArray("fftXData");
        Double[] fftXData = new Double[jsonFftXData.size()];
        for(int i = 0;i<jsonFftYData.size();i++){
            fftXData[i] = jsonFftXData.getDouble(i);
        }

        out.setTimeYData(timeYData);
        out.setTimeXData(timeXData);
        out.setFftYData(fftYData);
        out.setFftXData(fftXData);

        double pk = jsonRes.getDouble("pk");
        out.setPk(pk);
        double pk2pk = jsonRes.getDouble("pk2pk");
        out.setPk2pk(pk2pk);
        double rm = jsonRes.getDouble("rm");
        out.setRm(rm);

        Double ratio = out.getRatio();
        Double offset = out.getOffset();
        if(type.equals("acc") && ratio >= 0 && offset >=0){
            double tmpPk = out.getPk()*ratio + offset;
            out.setPk(tmpPk);
        }
        if(type.equals("speed") && ratio > 0 && offset >=0){
            double tmpRm = out.getRm()*ratio + offset;
            out.setRm(tmpRm);
        }
        if(type.equals("distance") && ratio > 0 && offset >=0){
            double tmpPk2pk = out.getPk2pk()*ratio + offset;
            out.setPk2pk(tmpPk2pk);
        }

        int index = 0;
        ArrayList<Double> fft_list = new ArrayList<>(Arrays.asList(fftYData));
        List<Double> freq_list = new ArrayList<>(Arrays.asList(fftXData));
        double fft_first = Collections.max(fft_list);
        out.setFf_first(fft_first);
        index = fft_list.indexOf(fft_first);
        double freq_first = freq_list.get(index);
        out.setFreq_first(freq_first);
        fft_list.remove(index);
        freq_list.remove(index);

        double fft_second = Collections.max(fft_list);
        out.setFf_second(fft_second);
        index = fft_list.indexOf(fft_second);
        double freq_second = freq_list.get(index);
        out.setFreq_second(freq_second);
        fft_list.remove(index);
        freq_list.remove(index);

        double fft_third = Collections.max(fft_list);
        out.setFf_third(fft_third);
        index = fft_list.indexOf(fft_third);
        double freq_third = freq_list.get(index);
        out.setFreq_third(freq_third);
        fft_list.remove(index);
        freq_list.remove(index);

        bis.close();
        dis.close();

        return out;
    }

    public static FftChartEntity accFromByte(byte[] data) throws IOException {
        return fromByte(data, "acc");
    }

    public static FftChartEntity speedFromByte(byte[] data) throws IOException {
        return fromByte(data, "speed");
    }

    public static FftChartEntity distanceFromByte(byte[] data) throws IOException {
        return fromByte(data, "distance");
    }
}
