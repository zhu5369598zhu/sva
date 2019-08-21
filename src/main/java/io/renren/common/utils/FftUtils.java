package io.renren.common.utils;

import io.renren.modules.inspection.entity.FftChartEntity;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.util.FastMath;
import org.jtransforms.fft.DoubleFFT_1D;

import java.io.*;
import java.util.*;
import java.util.stream.DoubleStream;



public class FftUtils {

    public static double[] toAmend(Integer[] sourceData, Double ration){
        DoubleStream doubleStream = Arrays.stream(sourceData).mapToDouble(a->a);
        OptionalDouble avg = Arrays.stream(sourceData).mapToDouble(a->a).average();
        return doubleStream.map(a->((a-avg.getAsDouble())/ration)).toArray();
    }

    public static double rms(double[] data){
        DoubleStream in = Arrays.stream(data);
        return FastMath.sqrt(in.map(it->it*it).average().getAsDouble());
    }

    public static double[] doFft(double[] data){
        int n = data.length;
        double[] in = new double[n*2];
        for(int i = 0; i < data.length; i++){
            in[i*2] = data[i];
        }
        DoubleFFT_1D fft = new DoubleFFT_1D(n*2);
        fft.realForward(in);
        in[1] = 0.0;
        double[] out = new double[n/2];
        for(int i = 0; i < out.length; i++){
            out[i] = FastMath.sqrt(in[i*2]*in[i*2] + in[i*2 + 1]*in[i*2 + 1]);
            out[i] = out[i]/n;
        }

        return out;
    }

    public static double[] integral(double[] data){
        int n = data.length;
        double[] out = new double[data.length];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < i; j++){
                out[i] += data[j];
            }
        }

        return out;
    }

    public static double[] polyFit(double[] data, int degree){
        int n = data.length;
        double[] offset = new double[n];
        WeightedObservedPoints wop = new WeightedObservedPoints();
        for(int i = 0; i< n; i++){
            wop.add((double)i, data[i]);
        }
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
        double[] fit = fitter.fit(wop.toList());
        for(int i = 0; i< n; i++){
            offset[i] = fit[1]*i + fit[0];
        }

        return offset;
    }

    public static double[] calculate(double[] data, double Fs){
        double[] src = integral(data);
        int n = src.length;
        double[] offset = polyFit(src,1);
        double[] result = new double[n];
        for(int i = 0; i < n; i++){
            result[i] = (src[i] - offset[i])/Fs;
        }

        return result;
    }

    public static FftChartEntity fromByte(byte[] data, String type) throws  IOException{
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

        Integer dataLen  = dis.readInt();
        Integer[] sourceData = new Integer[dataLen];
        for(int k = 0; k < dataLen; k++){
            sourceData[k] = dis.readInt();
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < sourceData.length; i++){
            sb.append(sourceData[i]).append("\n");
        }
        sb.deleteCharAt(sb.length() -1);
        BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"));
        bw.write(sb.toString());
        bw.close();

        double[] k = toAmend(sourceData, out.getRatio());


        Integer[] t = new Integer[dataLen];
        for(int i = 0; i < dataLen; i++){
            t[i] = i;
        }


        if(type.equals("acc")){

        } else if(type.equals("speed")){
            k = calculate(k, out.getFs()/1000);
        }else if(type.equals("distance")){
            k = calculate(k, out.getFs());
            k = calculate(k, out.getFs()/1000/1000);
        }

        out.setTimeYData(ArrayUtils.toObject(k));
        out.setTimeXData(t);
        double pk = Arrays.stream(k).map(it->FastMath.abs(it)).max().getAsDouble();
        out.setPk(pk);
        double max = Arrays.stream(k).max().getAsDouble();
        double min = Arrays.stream(k).min().getAsDouble();
        double pk2pk = max - min;
        out.setPk2pk(pk2pk);
        double rm = rms(k);
        out.setRm(rm);

        double[] fft = doFft(k);
        Double[] fft_d = ArrayUtils.toObject(fft);
        out.setFftYData(fft_d);
        double[] freq = new double[fft.length];
        for(int i = 0; i < fft.length; i++){
            double a = out.getFs()/2.0/fft.length;
            freq[i] = out.getFs()/2.0/fft.length*i;
        }
        Double[] freq_d = ArrayUtils.toObject(freq);
        out.setFftXData(freq_d);

        int index = 0;

        ArrayList<Double> fft_list = new ArrayList<>(Arrays.asList(fft_d));
        List<Double> freq_list = new ArrayList<>(Arrays.asList(freq_d));
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

    public static  FftChartEntity accFromByte(byte[] data) throws IOException {
        return fromByte(data, "acc");
    }

    public static  FftChartEntity speedFromByte(byte[] data) throws IOException {
        return fromByte(data, "speed");
    }

    public static  FftChartEntity distanceFromByte(byte[] data) throws IOException {
        return fromByte(data, "distance");
    }
}
