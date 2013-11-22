package fractalzoomer.palettes;


import fractalzoomer.main.MainWindow;
import fractalzoomer.core.ThreadDraw;
import java.awt.Color;
import java.awt.image.BufferedImage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class Palette extends ThreadDraw {
  private static final int[] default_palette = {-16774636, -16510682, -16246472, -15982517, -15718307, -15454353, -15190398, -14860652, -14596442, -14332487, -14068277, -13804323, -13474576, -13673506, -13806644, -14005318, -14138456, -14337130, -14470267, -14668941, -14802079, -15000753, -15133891, -15332565, -15465702, -14350567, -13169639, -12054248, -10873320, -9758185, -8642793, -7461866, -6280938, -5165803, -3984875, -2869484, -1688556, -2869485, -3984622, -5099759, -6214896, -7330033, -8445170, -9560563, -10675700, -11790837, -12905974, -14021111, -15136247, -14018808, -12901369, -11783674, -10666234, -9548795, -8431356, -7313661, -6196221, -5078526, -3961087, -2843648, -1660416, -2842880, -3959807, -5142270, -6259197, -7441660, -8558332, -9740795, -10857722, -12040185, -13157112, -14339575, -15456246, -15586039, -15715832, -15780089, -15909882, -16039675, -16103931, -16233724, -16363517, -16427774, -16557567, -16687360, -16751616, -16753664, -16755455, -16691966, -16693757, -16630268, -16632060, -16634107, -16570362, -16572409, -16508664, -16510711, -16446966, -15331573, -14216179, -13100785, -11985392, -10869998, -9754604, -8639467, -7523817, -6408423, -5293030, -4177636, -2996706, -3653601, -4310495, -4967389, -5624284, -6281178, -6937816, -7594711, -8251605, -8908499, -9565394, -10287824, -10878926, -10418377, -9892035, -9431485, -8905143, -8444593, -7918507, -7457702, -6931360, -6470810, -5944468, -5483918, -4957576, -5942159, -6926742, -7911324, -8895907, -9880490, -10864816, -11849399, -12833982, -13818564, -14803147, -15787730, -16772056, -16639947, -16442302, -16310192, -16112547, -15980438, -15848073, -15650427, -15452782, -15320672, -15123027, -14990918, -14793016, -14990919, -15123286, -15321189, -15453556, -15651459, -15783826, -15981729, -16114096, -16311999, -16444366, -16642525};
  private static final int[] spectrum_palette = {-16770817, -16181505, -15591937, -15002369, -14412801, -13823233, -13168129, -12578561, -11988993, -11399425, -10809857, -10155009, -9565441, -8975873, -8386305, -7796737, -7207169, -6552065, -5962497, -5372929, -4783361, -4193793, -3538689, -3407626, -3211026, -3079962, -2883362, -2752299, -2555699, -2424635, -2228035, -2096972, -1900372, -1769308, -1572708, -1441645, -1245045, -1113981, -917381, -786318, -589718, -458654, -262054, -65454, -63922, -62390, -60602, -59070, -57538, -55750, -54218, -52686, -50898, -49366, -47833, -46045, -44513, -42981, -41193, -39661, -38129, -36341, -34809, -33277, -31488, -357888, -684032, -1010432, -1271040, -1597440, -1923584, -2249984, -2510592, -2836992, -3163136, -3423744, -3750144, -4076288, -4402688, -4663296, -4989696, -5315840, -5642240, -5902848, -6229248, -6555392, -6816000, -7340285, -7799033, -8257782, -8716530, -9175279, -9699563, -10158311, -10617060, -11075808, -11534557, -12058841, -12517590, -12976338, -13435086, -13893835, -14418119, -14876868, -15335616, -15794365, -16253113, -16711861, -16712621, -16713124, -16713628, -16714131, -16714635, -16715394, -16715897, -16716401, -16716904, -16717408, -16718167, -16718671, -16719174, -16719677, -16720181, -16720940, -16721444, -16721947, -16722451, -16722954, -16723457, -16725761, -16727809, -16730113, -16732161, -16734465, -16736513, -16738561, -16740865, -16742913, -16745217, -16747265, -16749313, -16751617, -16753665, -16755969, -16758017, -16760065, -16762369, -16764417, -16766721, -16768769};
  private static final int[] alternative_palette = {-16777025, -16252733, -15728441, -15204149, -14679856, -14090028, -13565736, -13041444, -12517151, -11992859, -11403031, -10878739, -10354446, -9830154, -9305862, -8716033, -8388368, -8060702, -7733037, -7339835, -7012170, -6684504, -6356839, -5963637, -5434765, -4905892, -4377019, -3848146, -3319273, -2724608, -2195712, -1666816, -1137920, -609024, -14336, -208128, -336384, -530176, -658432, -786688, -1121024, -1389568, -1658368, -1926912, -2195712, -2529792, -2798592, -3067136, -3335936, -3604480, -3932144, -4194272, -4456400, -4718528, -4980657, -5242785, -5504912, -5242753, -4980593, -4718433, -4456273, -4194113, -3931953, -3669793, -3407633, -3407118, -3341067, -3275016, -3208965, -3142657, -2943495, -2678540, -2413841, -2148886, -1884187, -1619232, -1354278, -1089579, -824624, -559925, -294970, -30015, -749378, -1468741, -2187848, -2907211, -3626318, -4868938, -6045765, -7222848, -8399675, -9576502, -10753585, -11930412, -13107239, -13830693, -14554146, -15343135, -16001052, -16724249, -16728597, -16732688, -16737035, -16741126, -16745217, -16749583, -16753693, -16757802, -16762168, -16766278, -16770387, -16767841, -16765295, -16762748, -16760202, -16757655, -16755109, -16752562, -16750016, -16747469, -16745151, -16742576, -16740002, -16737427, -16734853, -16732278, -16729703, -16727129, -16724554, -16721980, -16719405, -16716830, -16322585, -15928339, -15534093, -15139847, -14745601, -13830657, -12849921, -11869185, -10888449, -9907713, -8926977, -7946241, -6963457, -5980673, -4997633, -4014849, -3032065, -2049025, -1066241, -83201, -280065, -411137, -607745, -738817, -869889, -1001220, -1066758, -1197832, -1263370, -1328908, -1789975, -2185250, -2580781, -2976056, -3371331, -3832398, -4227673, -4622948, -5018479, -5413754, -5809029, -6270096, -6665371, -7060646, -7456178, -7851453, -8246727, -8707772, -9103024, -9498532, -9893784, -10289036, -10745729, -11136885, -11528041, -11919197, -12310354, -12701510, -13092666, -13483822, -13940532, -14331705, -14722879, -15114052, -15570761, -15961935, -16353108, -16744281, -16414815, -16085092, -15689834, -15360111, -15030389, -14700666, -14305407, -13975685, -13580426, -13250704, -12920981, -12525722, -12195986, -11800713, -11470976, -11075703, -10750063, -10424166, -10032733, -9706836, -9380939, -8989506, -8663609, -8337712, -7946279, -7620653, -7294771, -6903353, -6577471, -6251589, -5860171, -5534289, -5142871, -4816989, -4491107, -4099689, -3773807, -3382388, -4367226, -5286528, -6271366, -7190668, -8175506, -9094807, -9882767, -10670726, -11458686, -12180853, -12968812, -13756772, -14478939, -15266899, -16054858};
  private static final int[] alternative2_palette = {-6216672, -6938082, -7659492, -8380902, -9036520, -9757930, -10479340, -11200750, -11856368, -12577778, -13299188, -14020598, -14676216, -13689065, -12701914, -11714763, -10727611, -9740460, -8753565, -7700878, -6713470, -5726319, -4739168, -3752017, -2699329, -2765383, -2765900, -2831953, -2832471, -2898524, -2899041, -2899559, -2965612, -2966129, -3032183, -3032700, -3032961, -3296648, -3560334, -3823765, -4021915, -4285346, -4549032, -4812719, -5010613, -5274300, -5537730, -5801417, -5999311, -6066641, -6068434, -6070228, -6071765, -6139095, -6140888, -6142681, -6144219, -6211548, -6213342, -6215135};
  private static final int[] alternative3_palette = {-2701678, -2570090, -2438501, -2306912, -2109532, -1977943, -1846354, -1714766, -1517385, -1385796, -1254208, -1122619, -925238, -1779778, -2568782, -3357785, -4146789, -4935793, -5724796, -6513800, -7302804, -8091807, -8880811, -9670071, -10458818, -10326463, -10128572, -9930424, -9732533, -9599922, -9402030, -9204139, -9005992, -8873636, -8675489, -8477598, -8279450, -8543904, -8808101, -9007018, -9271215, -9470132, -9734329, -9998526, -10197443, -10461640, -10660557, -10924754, -11123415, -10465487, -9741766, -9018045, -8359860, -7636396, -6912675, -6254490, -5530769, -4807305, -4149120, -3425399};
  private static final int[] alternative4_palette = {-15990784, -15727612, -15398904, -15135732, -14807024, -14478573, -14215401, -13886693, -13557985, -13294813, -12966105, -12637397, -12374225, -12045517, -11716552, -11783116, -11849680, -11850451, -11917015, -11917786, -11984350, -11985121, -12051685, -12118249, -12119020, -12185584, -12186355, -12252919, -12253690, -11925239, -11530995, -11202544, -10808300, -10414313, -10085861, -9691618, -9297630, -8968922, -8574935, -8180691, -7852240, -7457996, -7063752, -6602430, -6141108, -5614250, -5152928, -4691605, -4165003, -3703681, -3242103, -2715501, -2254178, -1792856, -1265998, -804676, -277561, -1460808, -2578518, -3696228, -4813938, -5931649, -7049359, -8167069, -9284779, -10402489, -11520200, -12637910, -13755620, -14873330};
  private static final int[] alternative5_palette = {-4576252, -4440548, -4304588, -4168627, -4032667, -3896707, -3760746, -3624786, -3488826, -3352865, -4272434, -5191747, -6111060, -7030373, -7949685, -8868998, -9788311, -10707624, -11626937, -12480713, -11035853, -9590992, -8146132, -6701271, -5256410, -3811550, -2366689, -856292, -2435811, -3949793, -5463776, -6977758, -8491741, -10005723, -11519706, -13033688, -12109779, -11120077, -10195912, -9206210, -8216764, -7292599, -6302897, -5313195, -6494107, -7609227, -8724347, -9905003, -11020123, -12200779, -13315899, -14431019, -15546138, -14110488, -12609045, -11107859, -9606416, -8105230, -6603787, -6668314, -6667304, -6731831, -6730821, -6795348, -6794082, -6990956, -7187829, -7384703, -7515784, -7712657, -7909531, -8040612, -8237485, -8434359, -8631232, -8762313, -8959187, -9156060, -9287141, -8761308, -8235218, -7709385, -7183295, -6657462, -6131372, -5605539, -5079449, -4553616, -4027526, -3501693, -2975603, -2449513, -2713965, -2978160, -3242355, -3506551, -3771002, -3969661, -4233857, -4498052, -4762503, -5026699, -5290894, -5489553, -5424537, -5359265, -5293992, -5228720, -5163448, -5098175, -5032903, -4967887, -4902614, -4837342, -4772070, -4706797, -4641525};
  private static final int[] alternative6_palette = {-9903620, -9051663, -8133913, -7216419, -6364206, -5446712, -4528962, -3611468, -2759255, -1841761, -924011, -6261, -1388922, -2771582, -4154242, -5471366, -6854026, -8236686, -9553554, -9484943, -9416075, -9347463, -9278596, -9209728, -9141116, -9072248, -9003381, -8934769, -8865901, -8797033, -9454970, -10047115, -10639516, -11231660, -11889341, -12481742, -13073887, -13666031, -13403623, -13140958, -12878294, -12550093, -12287429, -12024764, -11762100, -11433899, -10646957, -9859759, -9072816, -8220082, -7433139, -6645941, -5793206, -5859770, -5860542, -5861313, -5927621, -5928392, -5929164, -5929935, -5996243, -5997014, -5997786, -5998557, -6064865, -6065636, -6066408, -6067179, -6198506, -6329832, -6460903, -6592229, -6723555, -6854626, -6920416, -7051742, -7182813, -7314139, -7445465, -7576536, -7707862, -7773396, -8490708, -9208020, -9859795, -10577107, -11228627, -11945938, -12597714, -13315026, -13966545, -13444552, -12856767, -12268982, -11681197, -11093412, -10505627, -9917842, -9395848, -8808063, -8220278, -7632493, -7044708, -6456923, -5869138, -5281352, -5869147, -6456941, -7044735, -7632529, -8220323, -8808117, -9395911, -9983705, -9982664, -9981366, -9980068, -9979026, -9977729, -9976431, -9975389, -9974091, -9972794, -9971752, -9970454};
  private static final int[] alternative7_palette = {-14297104, -13383963, -12405286, -11426353, -10447676, -9534279, -8555602, -7576925, -6597992, -5684851, -4705918, -3727242, -2748308, -2679965, -2545829, -2477229, -2343350, -2274750, -2140615, -2072271, -1938135, -1803999, -2265571, -2661607, -3057643, -3518958, -3914994, -4311030, -4706809, -6083576, -7394807, -8706038, -10082805, -11394035, -12770802, -14082033, -15393520, -16704494, -16570838, -16436925, -16237476, -16103563, -15969906, -15770457, -15636544, -15437095, -15632700, -15762513, -15957862, -16087674, -16283023, -16412836, -16608185, -16737997, -16742349, -16746701, -16751053, -16755405, -16759757, -16764109, -15777231, -14790352, -13803218, -12816339, -11829205, -10842326, -9855192, -8868313, -7881179, -6894300, -5907165, -5975243, -6043321, -6111398, -6179476, -6247554, -6315631, -6383709, -6451787, -6519864, -6587942, -6656020, -6723841, -6656011, -6588180, -6520349, -6386727, -6318896, -6251065, -6117443, -6049612, -5981781, -5848158, -5716568, -5519185, -5321803, -5190212, -4992830, -4795447, -4663857, -4466474, -4269092, -4071709, -5579292, -7021338, -8463384, -9970966, -11413012, -12855058};
  private static final int[] alternative8_palette = {-16777216, -16514044, -16250872, -15922164, -15658992, -15330284, -15067112, -14738404, -14475232, -14146524, -13883352, -13554388, -13291216, -13028044, -12699336, -12436165, -12107457, -11844285, -11515577, -11252405, -10923697, -10660525, -10331560, -9871009, -9410201, -8949650, -8488842, -8028291, -7567483, -7106931, -6580588, -6120036, -5659229, -5198677, -4737869, -4277318, -3816510, -3290423, -2829615, -2369063, -1908512, -1447704, -987153, -526345, -1, -132104, -264206, -330516, -462618, -529184, -661030, -793132, -859698, -991544, -1058110, -1189956, -1322059, -1388625, -1520471, -1587037, -1719139, -1850985, -1917551, -2049653, -2115963, -2248065, -2314375, -2577547, -2775183, -3038355, -3235991, -3499163, -3696799, -3894435, -4157351, -4354987, -4618159, -4815795, -5013431, -5276603, -5474239, -5737155, -5934791, -6132427, -6395599, -6593235, -6856407, -7054043, -7251423, -7580129, -7908578, -8237027, -8565476, -8893925, -9222374, -9550823, -9879273, -10207722, -10536171, -10864876, -11127789, -11456238, -11784687, -12113137, -12441586, -12770035, -13098484, -13426933, -13755382, -14083831, -14346744, -14478073, -14609401, -14740730, -14806522, -14937850, -15069179, -15134971, -15266555, -15397884, -15463676, -15595005, -15726333, -15791869, -15923198, -16054526, -16120318, -16251647, -16382975, -16448767, -16580096, -16711424, -16777216, -16776701, -16776186, -16775671, -16774900, -16774384, -16708333, -16707818, -16707047, -16706531, -16706016, -16639709, -16639194, -16638679, -16638163, -16637392, -16636877, -16570826, -16570310, -16569539, -16569024, -16568509, -16502201, -16501429, -16435121, -16434348, -16368040, -16367011, -16300703, -16234394, -16233622, -16167057, -16166285, -16099976, -16033668, -16032896, -15966331, -15965559, -15899250, -15832942, -15831913, -15765605, -15764832, -15698524, -15631959, -15632989, -15568226, -15503464, -15504493, -15439730, -15374968, -15310461, -15311234, -15246472, -15181965, -15117203, -15117976, -15053469, -14988707, -14989480, -14924973, -14860211, -14795448, -14796477, -14731715, -14666952, -14602189, -14734032, -14800082, -14931668, -14997719, -15129305, -15195611, -15327198, -15393248, -15524834, -15656421, -15722727, -15854313, -15920364, -15986414, -16118000, -16184051, -16315893, -16381943, -16513530, -16579580, -16711166};
  private static final int[] greenwhite_palette = {-14137846, -14134774, -14131446, -14128374, -14125046, -14121974, -14118646, -14115574, -14112246, -13716720, -13255657, -12794338, -12398812, -11937493, -11476430, -11080648, -10619585, -10158266, -8519836, -6815869, -5111902, -3407935, -1703968, -1, -1775648, -3551039, -5326429, -7101820, -8877211, -10652601, -12427992};
  private static final int[] blue_palette = {-16777152, -16777137, -16777121, -16777105, -16777089, -16777073, -16777057, -16777041, -16777025, -16777009, -16776993, -16776977, -16776961, -16771585, -16766209, -16760833, -16755201, -16749825, -16744449, -16739073, -16733441, -16728065, -16722689, -16717313, -16711681, -15925249, -15073281, -14221313, -13369345, -12517377, -11730945, -10878977, -10027009, -9175041, -8323073, -8719105, -9049601, -9379841, -9775873, -10106113, -10436609, -10832641, -11162881, -11493377, -11889153, -12219649, -12549889, -12880143, -13210141, -13474602, -13804600, -14069062, -14399059, -14663521, -14993775, -15323772, -15588234, -15918232, -16182693, -16512691};
  private static final int[] grayscale_palette = {-1, -789517, -1579033, -2302756, -3092272, -3815995, -4605511, -5395027, -6118750, -6908266, -7631989, -8421505, -9211021, -9934744, -10724260, -11447983, -12237499, -13027015, -13750738, -14540254, -15263977, -16053493, -16777216, -15987700, -15198184, -14408668, -13619152, -12829636, -12040120, -11184811, -10395295, -9605779, -8816263, -8026747, -7237231, -6447715, -5592406, -4802890, -4013374, -3223858, -2434342, -1644826, -855310};
  private static final int[] earthsky_palette = {-1, -1824, -3390, -4957, -6523, -8089, -9656, -11222, -12788, -803572, -1528820, -2253812, -2979059, -3769843, -4494835, -5220083, -5945074, -6536685, -7128039, -7654114, -8245468, -8836823, -9362897, -9954252, -10480070, -11004863, -11464120, -11923121, -12382378, -12841635, -13300636, -13759893, -14218894, -14546054, -14873214, -15200374, -15527534, -15854694, -16181854, -16509014, -16770638, -16702533, -16568635, -16500530, -16366632, -16298526, -16164629, -16096523, -15962625, -13991937, -12021249, -9985025, -8014081, -6043393, -4007169, -2036481};
  private static final int[] hotcold_palette = {-1, -1905153, -3744513, -5649409, -7488769, -9393665, -11233025, -13137921, -14977281, -15044107, -15110676, -15177502, -15244071, -15310640, -15377466, -15444035, -15510604, -15380311, -15184482, -15053933, -14858103, -14662274, -14531725, -14335896, -14139810, -13747364, -13289381, -12831399, -12373160, -11980713, -11522731, -11064748, -10606509, -9951909, -9231773, -8511381, -7791245, -7136645, -6416253, -5696117, -4975725, -4385638, -3795550, -3139926, -2549838, -1959751, -1304127, -714039, -58415, -51242, -43812, -36638, -29208, -22035, -14605, -7431};
  private static final int[] hotcold2_palette = {-51200, -49916, -48375, -47090, -45549, -44008, -42723, -41182, -39642, -38357, -36816, -35275, -33990, -32449, -31164, -29624, -28083, -26798, -25257, -23716, -22431, -20890, -19349, -18833, -18316, -17800, -17283, -16767, -15994, -15478, -14961, -14445, -13928, -13156, -12639, -12123, -11606, -11090, -10573, -9801, -9284, -8768, -8252, -7735, -6962, -72496, -72238, -137516, -137514, -202791, -202533, -268067, -267809, -333086, -333084, -332826, -398104, -398102, -463379, -463121, -528655, -528397, -593674, -593672, -658950, -658692, -658433, -789761, -855553, -986881, -1052673, -1184001, -1249537, -1315329, -1446657, -1512449, -1643777, -1709313, -1775105, -1906433, -1972225, -2103553, -2169345, -2234881, -2366209, -2432001, -2563329, -2629121, -2694657, -2825985, -2891777, -2957569, -3088897, -3154433, -3220225, -3286017, -3417345, -3482881, -3548673, -3614465, -3745793, -3811585, -3877121, -4008449, -4074241, -4140033, -4205569, -4336897, -4402689, -4468481, -4534017, -4665345, -4731137, -4862465, -4928257, -5059585, -5125377, -5191169, -5322497, -5388289, -5519617, -5585153, -5650945, -5782273, -5848065, -5979393, -6045185, -6110977, -6242305, -6308097, -6439425, -6505217, -6570753, -6310157, -5984025, -5723428, -5397296, -5136699, -4810567, -4549971, -4223838, -3963242, -3637365, -3311233, -3050637, -2724248, -2463652, -2137519, -1876923, -1550791, -1290194, -964062, -703465, -377333};
  private static final int[] fire_palette = {-16777216, -15073280, -13303808, -11534336, -9764864, -8060928, -6291456, -4521984, -2752512, -2423552, -2094336, -1765376, -1436160, -1106944, -777984, -448768, -54016, -52480, -50688, -48896, -47104, -45312, -43520, -41728, -39936, -38400, -36608, -34816, -33024, -31232, -29440, -27648, -25856, -24320, -22528, -20736, -18944, -17152, -15360, -13568, -11776, -10491, -8950, -7665, -6124, -4583, -3298, -1757, -215, -2105565, -4210914, -6316263, -8421612, -10526961, -12632310, -14737659};
  
     
     public Palette(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, boolean d3, int detail, double fiX, double fiY, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_val, double[] initial_vals, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, d3, detail, fiX, fiY, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type,  burning_ship, mandel_grass, mandel_grass_vals,  function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, coefficients, z_exponent_nova, relaxation, nova_method);

        switch (color_choice) {
            
            case 0:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(default_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(default_palette);
                }
                break;
            case 1:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(spectrum_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(spectrum_palette);
                }
                break;
            case 2:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative_palette);
                }
                break;
            case 3:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative2_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative2_palette);
                }
                break;
            case 4:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative3_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative3_palette);
                }
                break;
            case 5:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative4_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative4_palette);
                }
                break;
            case 6:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative5_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative5_palette);
                }
                break;   
            case 7:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative6_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative6_palette);
                }
                break;
            case 8:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative7_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative7_palette);
                }
                break;
            case 9:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative8_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative8_palette);
                }
                break;
            case 10:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(greenwhite_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(greenwhite_palette);
                }
                break;
            case 11:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(blue_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(blue_palette);
                }
                break;
            case 12:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(grayscale_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(grayscale_palette);
                }
                break;
            case 13:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(earthsky_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(earthsky_palette);
                }
                break;
            case 14:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(hotcold_palette);
                }
                break;
            case 15:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold2_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(hotcold2_palette);
                }
                break;
            case 16:
               if(!smoothing) {
                    palette_color = new PaletteColorNormal(fire_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(fire_palette);
                }
                break;
                
        }

    }

    public Palette(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, boolean d3, int detail, double fiX, double fiY, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing,  boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method,  double xJuliaCenter, double yJuliaCenter) {

       super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, d3, detail, fiX, fiY, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type,  burning_ship, mandel_grass, mandel_grass_vals,  function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, xJuliaCenter, yJuliaCenter);

       switch (color_choice) {
            
            case 0:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(default_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(default_palette);
                }
                break;
            case 1:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(spectrum_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(spectrum_palette);
                }
                break;
            case 2:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative_palette);
                }
                break;
            case 3:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative2_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative2_palette);
                }
                break;
            case 4:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative3_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative3_palette);
                }
                break;
            case 5:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative4_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative4_palette);
                }
                break;
            case 6:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative5_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative5_palette);
                }
                break;   
            case 7:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative6_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative6_palette);
                }
                break;
            case 8:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative7_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative7_palette);
                }
                break;
            case 9:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative8_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative8_palette);
                }
                break;
            case 10:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(greenwhite_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(greenwhite_palette);
                }
                break;
            case 11:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(blue_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(blue_palette);
                }
                break;
            case 12:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(grayscale_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(grayscale_palette);
                }
                break;
            case 13:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(earthsky_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(earthsky_palette);
                }
                break;
            case 14:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(hotcold_palette);
                }
                break;
            case 15:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold2_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(hotcold2_palette);
                }
                break;
            case 16:
               if(!smoothing) {
                    palette_color = new PaletteColorNormal(fire_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(fire_palette);
                }
                break;
                
        }


    }
    
    public Palette(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing,  boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, periodicity_checking, plane_type,  burning_ship, mandel_grass, mandel_grass_vals,  function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method);
        
       switch (color_choice) {
            
            case 0:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(default_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(default_palette);
                }
                break;
            case 1:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(spectrum_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(spectrum_palette);
                }
                break;
            case 2:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative_palette);
                }
                break;
            case 3:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative2_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative2_palette);
                }
                break;
            case 4:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative3_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative3_palette);
                }
                break;
            case 5:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative4_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative4_palette);
                }
                break;
            case 6:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative5_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative5_palette);
                }
                break;   
            case 7:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative6_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative6_palette);
                }
                break;
            case 8:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative7_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative7_palette);
                }
                break;
            case 9:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative8_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative8_palette);
                }
                break;
            case 10:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(greenwhite_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(greenwhite_palette);
                }
                break;
            case 11:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(blue_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(blue_palette);
                }
                break;
            case 12:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(grayscale_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(grayscale_palette);
                }
                break;
            case 13:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(earthsky_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(earthsky_palette);
                }
                break;
            case 14:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(hotcold_palette);
                }
                break;
            case 15:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold2_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(hotcold2_palette);
                }
                break;
            case 16:
               if(!smoothing) {
                    palette_color = new PaletteColorNormal(fire_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(fire_palette);
                }
                break;
                
        }


    }

    public Palette(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing,  boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double [] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, in_coloring_algorithm, smoothing, filters, filters_options_vals,  burning_ship, mandel_grass, mandel_grass_vals,  function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, xJuliaCenter, yJuliaCenter);

        switch (color_choice) {
            
            case 0:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(default_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(default_palette);
                }
                break;
            case 1:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(spectrum_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(spectrum_palette);
                }
                break;
            case 2:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative_palette);
                }
                break;
            case 3:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative2_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative2_palette);
                }
                break;
            case 4:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative3_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative3_palette);
                }
                break;
            case 5:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative4_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative4_palette);
                }
                break;
            case 6:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative5_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative5_palette);
                }
                break;   
            case 7:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative6_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative6_palette);
                }
                break;
            case 8:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative7_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative7_palette);
                }
                break;
            case 9:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative8_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative8_palette);
                }
                break;
            case 10:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(greenwhite_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(greenwhite_palette);
                }
                break;
            case 11:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(blue_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(blue_palette);
                }
                break;
            case 12:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(grayscale_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(grayscale_palette);
                }
                break;
            case 13:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(earthsky_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(earthsky_palette);
                }
                break;
            case 14:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(hotcold_palette);
                }
                break;
            case 15:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold2_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(hotcold2_palette);
                }
                break;
            case 16:
               if(!smoothing) {
                    palette_color = new PaletteColorNormal(fire_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(fire_palette);
                }
                break;
                
        }


    }

    public Palette(int color_choice, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, boolean smoothing,  BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, image, color_cycling_location);
      
        switch (color_choice) {
            
            case 0:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(default_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(default_palette);
                }
                break;
            case 1:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(spectrum_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(spectrum_palette);
                }
                break;
            case 2:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative_palette);
                }
                break;
            case 3:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative2_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative2_palette);
                }
                break;
            case 4:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative3_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative3_palette);
                }
                break;
            case 5:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative4_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative4_palette);
                }
                break;
            case 6:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative5_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative5_palette);
                }
                break;   
            case 7:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative6_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative6_palette);
                }
                break;
            case 8:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative7_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative7_palette);
                }
                break;
            case 9:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative8_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative8_palette);
                }
                break;
            case 10:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(greenwhite_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(greenwhite_palette);
                }
                break;
            case 11:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(blue_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(blue_palette);
                }
                break;
            case 12:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(grayscale_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(grayscale_palette);
                }
                break;
            case 13:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(earthsky_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(earthsky_palette);
                }
                break;
            case 14:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(hotcold_palette);
                }
                break;
            case 15:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold2_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(hotcold2_palette);
                }
                break;
            case 16:
               if(!smoothing) {
                    palette_color = new PaletteColorNormal(fire_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(fire_palette);
                }
                break;
                
        }


    }

    public Palette(int color_choice, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, boolean smoothing,  boolean[] filters, int[] filters_options_vals) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, color_cycling_location, filters, filters_options_vals);
        
        switch (color_choice) {
            
            case 0:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(default_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(default_palette);
                }
                break;
            case 1:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(spectrum_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(spectrum_palette);
                }
                break;
            case 2:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative_palette);
                }
                break;
            case 3:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative2_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative2_palette);
                }
                break;
            case 4:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative3_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative3_palette);
                }
                break;
            case 5:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative4_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative4_palette);
                }
                break;
            case 6:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative5_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative5_palette);
                }
                break;   
            case 7:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative6_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative6_palette);
                }
                break;
            case 8:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative7_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative7_palette);
                }
                break;
            case 9:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(alternative8_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(alternative8_palette);
                }
                break;
            case 10:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(greenwhite_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(greenwhite_palette);
                }
                break;
            case 11:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(blue_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(blue_palette);
                }
                break;
            case 12:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(grayscale_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(grayscale_palette);
                }
                break;
            case 13:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(earthsky_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(earthsky_palette);
                }
                break;
            case 14:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(hotcold_palette);
                }
                break;
            case 15:
                if(!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold2_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(hotcold2_palette);
                }
                break;
            case 16:
               if(!smoothing) {
                    palette_color = new PaletteColorNormal(fire_palette);
                }
                else {
                    palette_color = new PaletteColorSmooth(fire_palette);
                }
                break;
                
        }


    }
    
    public Palette(int FROMx, int TOx, int FROMy, int TOy, int detail, double fiX, double fiY,  MainWindow ptr, BufferedImage image, boolean[] filters, int[] filters_options_vals) {
        
        super(FROMx, TOx, FROMy, TOy, detail, fiX, fiY,  ptr, image, filters, filters_options_vals);
        
    }

    
}
