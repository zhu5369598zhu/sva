package io.renren.modules.inspection.entity;

public class FftChartEntity {
    private Double ratio = 48.6;
    private Double offset = 0.0;
    private Integer cmd = 0;
    private Integer axles = 0;
    private Integer channel = 0;
    private Double Fs = 0.0;
    private Integer N = 0;
    private Double[] timeYData;
    private Double[] timeXData;
    private Double[] fftYData;
    private Double[] fftXData;
    private Double pk = null;
    private Double pk2pk = null;
    private Double rm = null;
    private Double ff_first = null;
    private Double freq_first = null;
    private Double ff_second = null;
    private Double freq_second = null;
    private Double ff_third = null;
    private Double freq_third = null;

    public Double getRatio() {
        return ratio;
    }

    public Double getOffset() {
        return offset;
    }

    public Integer getCmd() {
        return cmd;
    }

    public Integer getAxles() {
        return axles;
    }

    public Integer getChannel() {
        return channel;
    }

    public Double getFs() {
        return Fs;
    }

    public Integer getN() {
        return N;
    }

    public Double[] getTimeYData() {
        return timeYData;
    }

    public Double[] getTimeXData() {
        return timeXData;
    }

    public Double[] getFftYData() {
        return fftYData;
    }

    public Double[] getFftXData() {
        return fftXData;
    }

    public Double getPk() {
        return pk;
    }

    public Double getPk2pk() {
        return pk2pk;
    }

    public Double getRm() {
        return rm;
    }

    public Double getFf_first() {
        return ff_first;
    }

    public Double getFreq_first() {
        return freq_first;
    }

    public Double getFf_second() {
        return ff_second;
    }

    public Double getFreq_second() {
        return freq_second;
    }

    public Double getFf_third() {
        return ff_third;
    }

    public Double getFreq_third() {
        return freq_third;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public void setOffset(Double offset) {
        this.offset = offset;
    }

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }

    public void setAxles(Integer axles) {
        this.axles = axles;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public void setFs(Double fs) {
        Fs = fs;
    }

    public void setN(Integer n) {
        N = n;
    }

    public void setTimeYData(Double[] timeYData) {
        this.timeYData = timeYData;
    }

    public void setTimeXData(Double[] timeXData) {
        this.timeXData = timeXData;
    }

    public void setFftYData(Double[] fftYData) {
        this.fftYData = fftYData;
    }

    public void setFftXData(Double[] fftXData) {
        this.fftXData = fftXData;
    }

    public void setPk(Double pk) {
        this.pk = pk;
    }

    public void setPk2pk(Double pk2pk) {
        this.pk2pk = pk2pk;
    }

    public void setRm(Double rm) {
        this.rm = rm;
    }

    public void setFf_first(Double ff_first) {
        this.ff_first = ff_first;
    }

    public void setFreq_first(Double freq_first) {
        this.freq_first = freq_first;
    }

    public void setFf_second(Double ff_second) {
        this.ff_second = ff_second;
    }

    public void setFreq_second(Double freq_second) {
        this.freq_second = freq_second;
    }

    public void setFf_third(Double ff_third) {
        this.ff_third = ff_third;
    }

    public void setFreq_third(Double freq_third) {
        this.freq_third = freq_third;
    }
}
