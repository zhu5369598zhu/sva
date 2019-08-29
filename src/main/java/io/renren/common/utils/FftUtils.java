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

    public static double[] PARAMS={
            -0.009671081137159767693312062419863650575,
            -0.007575489511402185710542411811729834881,
            -0.010256923209183577833103484522325743455,
            -0.01333305358598517280366291259952049586,
            -0.016752662890156009428688221873926522676,
            -0.020452797710451584706214944731073046569,
            -0.024362443006902021608306441180502588395,
            -0.028386392495261671825579341543743794318,
            -0.032415480350254828600942147431851481088,
            -0.036340878693658935916221963680072803982,
            -0.040049240412368417030286593671917216852,
            -0.043421089615287039586988271366863045841,
            -0.046345234244478804297440177606404176913,
            -0.048728933248981136372446343330011586659,
            -0.050495018614525939704140711228319560178,
            -0.051581376089493168046740123600102378987,
            0.948052025477250204232859687181189656258,
            -0.051581376089493168046740123600102378987,
            -0.050495018614525939704140711228319560178,
            -0.048728933248981136372446343330011586659,
            -0.046345234244478804297440177606404176913,
            -0.043421089615287039586988271366863045841,
            -0.040049240412368417030286593671917216852,
            -0.036340878693658935916221963680072803982,
            -0.032415480350254828600942147431851481088,
            -0.028386392495261671825579341543743794318,
            -0.024362443006902021608306441180502588395,
            -0.020452797710451584706214944731073046569,
            -0.016752662890156009428688221873926522676,
            -0.01333305358598517280366291259952049586,
            -0.010256923209183577833103484522325743455,
            -0.007575489511402185710542411811729834881,
            -0.009671081137159767693312062419863650575};

    public static double[] toFilter(double[] sourceData, double[] params) {
        int length = sourceData.length;
        int n = params.length - 1;
        double[] y = new double[length - n];
        for (int i = n; i < length; i++) {
            for (int j = 0, k = i; j <= n; j++, k--) {
                y[i - n] += params[j] * sourceData[k];
            }
        }
        return y;
    }

    public static double[] toAmend(double[] sourceData, Double ration) {
        OptionalDouble avg = Arrays.stream(sourceData).map(a -> a).average();
        return Arrays.stream(sourceData).map(a -> ((a - avg.getAsDouble()) / ration)).toArray();
    }

    public static double rms(double[] data) {
        DoubleStream in = Arrays.stream(data);
        return FastMath.sqrt(in.map(it -> it * it).average().getAsDouble());
    }

    public static double[] doFft(double[] data) {
        int n = data.length;
        double[] in = new double[n * 2];
        for (int i = 0; i < data.length; i++) {
            in[i * 2] = data[i];
        }
        DoubleFFT_1D fft = new DoubleFFT_1D(n * 2);
        fft.realForward(in);
        in[1] = 0.0;
        double[] out = new double[n / 2];
        for (int i = 0; i < out.length; i++) {
            out[i] = FastMath.sqrt(in[i * 2] * in[i * 2] + in[i * 2 + 1] * in[i * 2 + 1]);
            out[i] = out[i] / n;
        }

        return out;
    }

    public static double[] integral(double[] data) {
        int n = data.length;
        double[] out = new double[data.length];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                out[i] += data[j];
            }
        }

        return out;
    }

    public static double[] polyFit(double[] data, int degree) {
        int n = data.length;
        double[] offset = new double[n];
        WeightedObservedPoints wop = new WeightedObservedPoints();
        for (int i = 0; i < n; i++) {
            wop.add((double) i, data[i]);
        }
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
        double[] fit = fitter.fit(wop.toList());
        for (int i = 0; i < n; i++) {
            offset[i] = fit[1] * i + fit[0];
        }

        return offset;
    }

    public static double[] calculate(double[] data, double Fs) {
        double[] src = integral(data);
        int n = src.length;
        double[] offset = polyFit(src, 1);
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = (src[i] - offset[i]) / Fs;
        }

        return result;
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
            //out.ratio = params[0];
            out.setOffset(params[1].doubleValue());
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
        double[] sourceData = new double[dataLen];
        for (int k = 0; k < dataLen; k++) {
            Integer tmp = dis.readInt();
            sourceData[k] = tmp.doubleValue();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sourceData.length; i++) {
            sb.append(sourceData[i]).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        //BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"));
        //bw.write(sb.toString());
        //bw.close();

        double[] y = toFilter(sourceData, PARAMS);
        double[] k = toAmend(y, out.getRatio());


        Integer[] t = new Integer[dataLen];
        for (int i = 0; i < dataLen; i++) {
            t[i] = i;
        }


        if (type.equals("acc")) {

        } else if (type.equals("speed")) {
            k = calculate(k, out.getFs() / 1000);
        } else if (type.equals("distance")) {
            k = calculate(k, out.getFs());
            k = calculate(k, out.getFs() / 1000 / 1000);
        }

        out.setTimeYData(ArrayUtils.toObject(k));
        out.setTimeXData(t);
        double pk = Arrays.stream(k).map(it -> FastMath.abs(it)).max().getAsDouble();
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
        for (int i = 0; i < fft.length; i++) {
            double a = out.getFs() / 2.0 / fft.length;
            freq[i] = out.getFs() / 2.0 / fft.length * i;
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
