package fractalzoomer.main.app_settings;

public class PostProcessSettings {
    public StatisticsSettings sts;
    public HistogramColoringSettings hss;
    public LightSettings ls;
    public OrbitTrapSettings ots;
    public ContourColoringSettings cns;
    public RainbowPaletteSettings rps;
    public OffsetColoringSettings ofs;
    public GreyscaleColoringSettings gss;
    public FakeDistanceEstimationSettings fdes;
    public SlopeSettings ss;
    public BumpMapSettings bms;
    public EntropyColoringSettings ens;
    public NumericalDistanceEstimatorSettings ndes;

    public PostProcessSettings() {
        sts = new StatisticsSettings();
        hss = new HistogramColoringSettings();
        ots = new OrbitTrapSettings();
        cns = new ContourColoringSettings();
        ls = new LightSettings();
        bms = new BumpMapSettings();
        ss = new SlopeSettings();
        ens = new EntropyColoringSettings();
        rps = new RainbowPaletteSettings();
        ofs = new OffsetColoringSettings();
        gss = new GreyscaleColoringSettings();
        fdes = new FakeDistanceEstimationSettings();
        ndes = new NumericalDistanceEstimatorSettings();
    }
}
