/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fractalzoomer.utils;

import java.util.ArrayList;

public class Cubehelix {
    public static final int[] defaultMap = {-16777216, -16777216, -16711679, -16711679, -16645886, -16645886, -16580350, -16580349, -16580349, -16514813, -16514812, -16449020, -16449019, -16449019, -16383482, -16383482, -16317946, -16317945, -16317689, -16252152, -16252152, -16252151, -16186615, -16186615, -16121078, -16120822, -16120821, -16055285, -16055284, -16055284, -15989747, -15989491, -15989490, -15923954, -15923954, -15923953, -15858417, -15858160, -15858160, -15858159, -15792623, -15792622, -15792366, -15726829, -15726829, -15726828, -15661292, -15661291, -15661035, -15661034, -15595498, -15595497, -15595497, -15595240, -15529704, -15529703, -15529703, -15529702, -15463910, -15463909, -15463909, -15463908, -15463652, -15398115, -15398115, -15398114, -15398114, -15397857, -15332321, -15332320, -15332320, -15332063, -15332063, -15266526, -15266526, -15266269, -15266269, -15266268, -15266268, -15266011, -15200475, -15200474, -15200474, -15200217, -15200217, -15200216, -15200216, -15134423, -15134423, -15134422, -15134166, -15134165, -15134165, -15134164, -15133908, -15133907, -15133907, -15068114, -15068114, -15068114, -15068113, -15067857, -15067856, -15067856, -15067599, -15067599, -15067598, -15067342, -15067341, -15067341, -15067340, -15067084, -15067084, -15067083, -15066827, -15066826, -15001290, -15001033, -15001033, -15001033, -15000776, -15000776, -15000775, -15000519, -15000518, -15000518, -15000262, -15000261, -15000005, -15000004, -15000004, -14999748, -14999747, -15065283, -15065026, -15065026, -15065026, -15064769, -15064769, -15064513, -15064512, -15064512, -15064256, -15064255, -15064255, -15063999, -15063998, -15063742, -15063742, -15063741, -15063485, -15063485, -15063228, -15063228, -15063228, -15062971, -15128507, -15128251, -15128250, -15128250, -15127994, -15127994, -15127737, -15127737, -15127737, -15127481, -15127480, -15127224, -15127224, -15127224, -15192503, -15192503, -15192247, -15192247, -15191990, -15191990, -15191990, -15191734, -15191734, -15191477, -15191477, -15191477, -15191221, -15256757, -15256501, -15256500, -15256244, -15256244, -15256244, -15255988, -15255988, -15255731, -15255731, -15255475, -15255475, -15255219, -15255219, -15320755, -15320499, -15320499, -15320242, -15320242, -15319986, -15319986, -15319986, -15319730, -15319730, -15319474, -15319474, -15319218, -15319218, -15318962, -15318962, -15384498, -15384242, -15384242, -15383986, -15383986, -15383730, -15383730, -15383473, -15383473, -15383473, -15383218, -15383218, -15382962, -15382962, -15382706, -15382706, -15382706, -15382450, -15382450, -15382194, -15382194, -15381938, -15381938, -15381682, -15381682, -15381682, -15381426, -15381426, -15381170, -15381170, -15380915, -15380915, -15380915, -15380659, -15380659, -15380403, -15380403, -15380403, -15380147, -15380148, -15379892, -15314356, -15314100, -15314100, -15314100, -15313844, -15313845, -15313589, -15313589, -15313589, -15313333, -15313333, -15313077, -15247542, -15247542, -15247286, -15247286, -15247030, -15247031, -15247031, -15246775, -15181239, -15180983, -15180983, -15180984, -15180728, -15180728, -15180728, -15114936, -15114937, -15114681, -15114681, -15114681, -15048890, -15048890, -15048890, -15048634, -15048634, -14983099, -14982843, -14982843, -14982587, -14917052, -14917052, -14916796, -14916796, -14916797, -14851005, -14851005, -14851005, -14785214, -14785214, -14785214, -14784958, -14719423, -14719423, -14719167, -14653631, -14653632, -14653632, -14587840, -14587840, -14587841, -14587585, -14522049, -14522049, -14456258, -14456258, -14456258, -14390722, -14390467, -14390467, -14324931, -14324675, -14324676, -14259140, -14259140, -14193348, -14193349, -14193349, -14127813, -14127557, -14062022, -14062022, -13996486, -13996230, -13996230, -13930695, -13930695, -13864903, -13864903, -13799368, -13799368, -13733576, -13733576, -13668041, -13668041, -13602505, -13602249, -13536713, -13536714, -13471178, -13471178, -13405386, -13405387, -13339851, -13339851, -13274315, -13274059, -13208524, -13208524, -13142988, -13077452, -13077452, -13011661, -13011661, -12946125, -12946125, -12880589, -12815053, -12814798, -12749262, -12749262, -12683726, -12618190, -12618190, -12552655, -12552399, -12486863, -12421327, -12421327, -12355791, -12290255, -12290255, -12224720, -12158928, -12158928, -12093392, -12093392, -12027856, -11962320, -11962320, -11896784, -11831248, -11831249, -11765457, -11699921, -11634385, -11634385, -11568849, -11503313, -11503313, -11437777, -11372241, -11372241, -11306705, -11241169, -11175377, -11175377, -11109841, -11044305, -11044305, -10978769, -10913233, -10847697, -10847697, -10782161, -10716625, -10651089, -10651089, -10585553, -10520017, -10520017, -10454481, -10388945, -10323409, -10323409, -10257873, -10192336, -10126800, -10126800, -10061264, -9995728, -9930192, -9929936, -9864400, -9798864, -9733327, -9667791, -9667791, -9602255, -9536719, -9471183, -9471182, -9405646, -9340110, -9274574, -9274574, -9209038, -9143501, -9077965, -9077965, -9012429, -8946892, -8881356, -8816076, -8816076, -8750540, -8685003, -8619467, -8619467, -8553930, -8488394, -8422858, -8422858, -8357321, -8291785, -8226249, -8160712, -8160712, -8095176, -8029639, -7964103, -7964103, -7898566, -7833030, -7767494, -7767493, -7701957, -7636421, -7570884, -7570884, -7505347, -7439811, -7374275, -7374274, -7308738, -7243201, -7243201, -7177664, -7112128, -7046592, -7046591, -6981055, -6915518, -6850238, -6850237, -6784701, -6719164, -6719164, -6653627, -6588091, -6522554, -6522554, -6457017, -6391481, -6391480, -6325944, -6260407, -6260407, -6194870, -6129334, -6129333, -6063796, -5998260, -5998259, -5932723, -5867186, -5867186, -5801649, -5736112, -5736112, -5670575, -5605039, -5605038, -5539501, -5539501, -5473964, -5408428, -5408427, -5342890, -5342890, -5277353, -5211816, -5211816, -5146279, -5146278, -5080742, -5015205, -5015204, -4949668, -4949667, -4884130, -4884130, -4818593, -4753056, -4753056, -4687519, -4687518, -4621981, -4621981, -4556444, -4556443, -4490907, -4490906, -4425369, -4425368, -4359832, -4359831, -4294294, -4294293, -4228757, -4228756, -4163219, -4163218, -4162962, -4097425, -4097424, -4031887, -4031887, -3966350, -3966349, -3966348, -3900812, -3900811, -3835274, -3835273, -3835272, -3769736, -3769735, -3703942, -3703941, -3703940, -3638404, -3638403, -3638402, -3572865, -3572864, -3572864, -3507327, -3507070, -3507069, -3441532, -3441532, -3441531, -3375994, -3375993, -3375992, -3310456, -3310199, -3310198, -3310197, -3244660, -3244660, -3244659, -3244658, -3178865, -3178864, -3178864, -3178863, -3113326, -3113325, -3113068, -3113068, -3113067, -3047530, -3047529, -3047528, -3047272, -3047271, -3047270, -2981733, -2981732, -2981476, -2981475, -2981474, -2981473, -2915936, -2915680, -2915679, -2915678, -2915677, -2915676, -2915420, -2915419, -2915418, -2849881, -2849624, -2849624, -2849623, -2849622, -2849621, -2849365, -2849364, -2849363, -2849362, -2849106, -2849105, -2849104, -2848847, -2848846, -2848846, -2848845, -2848588, -2783052, -2783051, -2783050, -2782793, -2782793, -2782792, -2782535, -2848070, -2848070, -2848069, -2847812, -2847812, -2847811, -2847554, -2847553, -2847553, -2847296, -2847295, -2847295, -2847038, -2847037, -2847037, -2846780, -2846779, -2846779, -2846522, -2846521, -2912057, -2911800, -2911799, -2911799, -2911542, -2911542, -2911541, -2911284, -2911284, -2911283, -2976562, -2976562, -2976305, -2976305, -2976304, -2976048, -2976047, -2976046, -2975790, -3041325, -3041069, -3041068, -3041068, -3040811, -3040810, -3040554, -3106089, -3106089, -3105832, -3105832, -3105575, -3105575, -3171110, -3170854, -3170853, -3170597, -3170596, -3170596, -3170339, -3235875, -3235618, -3235618, -3235362, -3235361, -3235361, -3300640, -3300640, -3300383, -3300383, -3300126, -3300126, -3365662, -3365405, -3365405, -3365148, -3365148, -3430428, -3430427, -3430427, -3430171, -3430170, -3429914, -3495449, -3495193, -3495193, -3494936, -3494936, -3494680, -3560215, -3560215, -3559959, -3559959, -3559702, -3559702, -3624982, -3624981, -3624725, -3624725, -3624725, -3624468, -3690004, -3689748, -3689747, -3689491, -3689491, -3689235, -3754771, -3754514, -3754514, -3754258, -3754258, -3754257, -3754001, -3819537, -3819281, -3819281, -3819024, -3819024, -3818768, -3818768, -3884048, -3884048, -3884047, -3883791, -3883791, -3883535, -3883535, -3883279, -3948815, -3948559, -3948558, -3948302, -3948302, -3948302, -3948046, -3948046, -3947790, -3947790, -4013070, -4013069, -4012813, -4012813, -4012557, -4012557, -4012557, -4012301, -4012301, -4012045, -4012045, -4011789, -4011789, -4011533, -4011533, -4011533, -4076813, -4076813, -4076557, -4076557, -4076301, -4076301, -4076301, -4076045, -4076045, -4075789, -4075789, -4075533, -4075533, -4075533, -4075277, -4075277, -4075021, -4075021, -4075021, -4074765, -4074765, -4008973, -4008973, -4008717, -4008717, -4008717, -4008461, -4008461, -4008205, -4008205, -4008205, -4007949, -4007949, -4007949, -4007693, -4007693, -3941901, -3941901, -3941901, -3941645, -3941646, -3941390, -3941390, -3941390, -3875598, -3875598, -3875598, -3875342, -3875342, -3875342, -3875086, -3809550, -3809550, -3809294, -3809294, -3809039, -3809039, -3743503, -3743247, -3743247, -3743247, -3742991, -3677455, -3677455, -3677199, -3677199, -3677199, -3611407, -3611408, -3611408, -3611408, -3545616, -3545616, -3545616, -3545360, -3479824, -3479824, -3479568, -3479568, -3414032, -3413776, -3413776, -3413776, -3348240, -3347985, -3347985, -3282449, -3282193, -3282193, -3216657, -3216657, -3216401, -3150865, -3150865, -3150865, -3150609, -3085073, -3085073, -3085073, -3019281, -3019281, -2953745, -2953745, -2953489, -2887953, -2887953, -2887953, -2822161, -2822161, -2822161, -2756625, -2756369, -2690833, -2690833, -2690833, -2625041, -2625041, -2559505, -2559505, -2559505, -2493713, -2493713, -2428177, -2428177, -2428177, -2362385, -2362385, -2296849, -2296849, -2296849, -2231057, -2231057, -2165521, -2165521, -2099985, -2099729, -2099728, -2034192, -2034192, -1968656, -1968656, -1902864, -1902864, -1837328, -1837328, -1837328, -1771535, -1771535, -1705999, -1705999, -1640463, -1640463, -1574671, -1574671, -1509134, -1509134, -1443598, -1443598, -1443598, -1377806, -1377805, -1312269, -1312269, -1246733, -1246733, -1180940, -1180940, -1115404, -1115404, -1049868, -1049867, -984331, -984075, -984075, -918538, -918538, -853002, -853002, -787465, -787209, -721673, -721673, -656136, -656136, -590600, -590600, -590599, -524807, -524807, -459270, -459270, -393734, -393733, -328197, -327941, -262405, -262404, -262404, -196868, -196867, -131331, -131330, -65538, -65538, -1, -1};
    public static final int[] cubehelix3 = {-16777216, -16776960, -16776960, -16776704, -16776704, -16776448, -16776192, -16776192, -16775936, -16775936, -16775680, -16775424, -16775424, -16775168, -16775168, -16774912, -16774656, -16774656, -16774400, -16774400, -16774144, -16773888, -16773888, -16773632, -16773632, -16773376, -16773376, -16773120, -16772864, -16772864, -16772608, -16772608, -16772351, -16772095, -16772095, -16771839, -16771839, -16771582, -16771582, -16771326, -16771070, -16771070, -16770813, -16770813, -16770557, -16770301, -16770300, -16770044, -16770044, -16769787, -16769787, -16769531, -16769530, -16769274, -16769018, -16769017, -16768761, -16768761, -16768504, -16768504, -16768247, -16768247, -16767991, -16767990, -16767734, -16767733, -16767477, -16767220, -16767220, -16766964, -16766963, -16766707, -16766706, -16766450, -16766449, -16766193, -16766192, -16765935, -16765935, -16765678, -16765678, -16765421, -16765421, -16765164, -16765163, -16764907, -16764906, -16764650, -16764649, -16764392, -16764392, -16764391, -16764135, -16764134, -16763877, -16763877, -16763620, -16763619, -16763362, -16763362, -16763361, -16763104, -16763104, -16762847, -16762846, -16762589, -16762589, -16762588, -16762331, -16762330, -16762074, -16762073, -16762072, -16761815, -16761814, -16761558, -16761557, -16761556, -16761299, -16761298, -16761042, -16761041, -16761040, -16760783, -16760782, -16760781, -16760524, -16760524, -16760523, -16760266, -16760265, -16760264, -16760007, -16760006, -16760005, -16759748, -16759747, -16759746, -16759490, -16759489, -16759488, -16759487, -16759230, -16759229, -16759228, -16758971, -16758970, -16758969, -16758968, -16758711, -16758710, -16758709, -16758708, -16758451, -16758450, -16758449, -16758448, -16758191, -16758190, -16758189, -16758188, -16757931, -16757930, -16757929, -16757928, -16757927, -16757670, -16757669, -16757668, -16757667, -16757666, -16757409, -16757408, -16757406, -16757405, -16757404, -16757403, -16757146, -16757145, -16757144, -16757143, -16757142, -16757141, -16757140, -16757139, -16756882, -16756881, -16756879, -16756878, -16756877, -16756876, -16756875, -16756874, -16756873, -16756616, -16756615, -16756614, -16756613, -16756611, -16756610, -16756609, -16756608, -16756607, -16756606, -16756605, -16756604, -16756603, -16756602, -16756600, -16756599, -16756598, -16756597, -16756340, -16756339, -16756338, -16756337, -16756336, -16756335, -16756334, -16756332, -16756331, -16756330, -16756329, -16756328, -16756583, -16756582, -16756581, -16756580, -16756579, -16756578, -16756577, -16756575, -16756574, -16756573, -16756572, -16756571, -16756570, -16756569, -16756568, -16756567, -16756566, -16756565, -16756564, -16756819, -16756818, -16756817, -16756816, -16756815, -16756814, -16756813, -16756812, -16756811, -16756809, -16757064, -16757063, -16757062, -16757061, -16757060, -16757059, -16757058, -16757057, -16757312, -16757312, -16757311, -16757310, -16757309, -16757308, -16757307, -16757562, -16692025, -16692024, -16626487, -16560950, -16495413, -16430132, -16430131, -16364594, -16299057, -16233520, -16167983, -16102703, -16037166, -16037165, -15971628, -15906091, -15840810, -15775273, -15709736, -15644200, -15578663, -15513382, -15447845, -15447844, -15382307, -15316771, -15251490, -15185953, -15120416, -15054879, -14989599, -14924062, -14858525, -14792988, -14727451, -14662171, -14596634, -14531097, -14465560, -14400280, -14334743, -14269206, -14203670, -14138389, -14072852, -14007315, -13941779, -13810706, -13745425, -13679889, -13614352, -13548815, -13483535, -13417998, -13352461, -13286925, -13221644, -13156108, -13090571, -12959498, -12894218, -12828681, -12763145, -12697864, -12632328, -12566791, -12501254, -12370438, -12304901, -12239365, -12173828, -12108548, -12043011, -11911939, -11846402, -11781122, -11715585, -11650049, -11584513, -11453697, -11388161, -11322625, -11257089, -11191809, -11060737, -10995201, -10929665, -10864385, -10798849, -10667777, -10602497, -10536961, -10471425, -10405889, -10275073, -10209537, -10144001, -10078465, -9947649, -9882113, -9816577, -9751041, -9685761, -9554689, -9489153, -9423617, -9358337, -9227265, -9161729, -9096193, -9030913, -8899841, -8834305, -8768769, -8703489, -8572417, -8506881, -8441345, -8376065, -8244993, -8179457, -8113921, -8048385, -7917569, -7852033, -7786497, -7720961, -7590145, -7524609, -7459073, -7393537, -7262465, -7197185, -7131649, -7066113, -6935041, -6869761, -6804225, -6738689, -6607617, -6542081, -6476801, -6411265, -6280193, -6214657, -6149121, -6083585, -5952769, -5887233, -5821697, -5756161, -5690625, -5559809, -5494273, -5428737, -5363201, -5232129, -5166593, -5101313, -5035777, -4904705, -4839169, -4773633, -4708097, -4642561, -4511745, -4446209, -4380673, -4315137, -4249601, -4118529, -4052993, -3987457, -3922177, -3856641, -3791105, -3660033, -3594497, -3528961, -3463425, -3397889, -3332353, -3201537, -3136001, -3070465, -3004929, -2939393, -2873857, -2808321, -2677249, -2611714, -2546178, -2480643, -2415107, -2349572, -2284036, -2218501, -2153221, -2022150, -1956614, -1891079, -1825543, -1760008, -1694472, -1628937, -1563401, -1497866, -1432331, -1366795, -1301260, -1235724, -1170189, -1104653, -1039118, -973583, -908047, -842512, -776976, -711441, -645906, -580370, -514579, -449044, -383508, -317973, -252438, -186902, -121367, -55832, -55832, -55833, -55834, -55834, -55835, -55836, -55581, -55581, -55582, -55583, -55584, -55584, -55585, -55586, -55331, -55331, -55332, -55333, -55334, -55334, -55335, -55080, -55081, -55082, -55082, -55083, -55084, -54829, -54830, -54830, -54831, -54832, -54577, -54578, -54578, -54579, -54580, -54325, -54326, -54327, -54327, -54072, -54073, -54074, -54075, -53820, -53821, -53821, -53822, -53567, -53568, -53569, -53314, -53315, -53316, -53316, -53061, -53062, -53063, -52808, -52809, -52810, -52555, -52555, -52556, -52301, -52302, -52303, -52048, -52049, -52050, -51795, -51795, -51540, -51541, -51542, -51287, -51288, -51289, -51034, -51035, -50780, -50780, -50781, -50526, -50527, -50272, -50273, -50018, -50019, -50020, -49764, -49765, -49510, -49511, -49256, -49257, -49002, -49003, -48747, -48748, -48493, -48494, -48239, -48240, -48241, -47986, -47986, -47731, -47476, -47477, -47222, -47223, -46968, -46968, -46713, -46714, -46459, -46460, -46205, -46205, -45950, -45951, -45696, -45441, -45442, -45186, -45187, -44932, -44933, -44678, -44678, -44423, -44168, -44169, -43914, -43914, -43659, -43404, -43405, -43149, -43150, -42895, -42640, -42640, -42385, -42130, -42131, -41875, -41876, -41621, -41365, -41366, -41111, -40856, -40856, -40601, -40602, -40346, -40091, -40092, -39836, -39581, -39582, -39326, -39071, -39071, -38816, -38561, -38561, -38306, -38051, -38051, -37796, -37540, -37541, -37285, -37030, -36775, -36775, -36520, -36264, -36265, -36009, -35754, -35754, -35499, -35243, -35244, -34988, -34733, -34477, -34478, -34222, -33967, -33967, -33712, -33456, -33200, -33201, -32945, -32690, -32690, -32435, -32179, -31923, -31924, -31668, -31412, -31413, -31157, -30901, -30646, -30646, -30390, -30135, -29879, -29879, -29624, -29368, -29368, -29112, -28857, -28601, -28601, -28345, -28089, -27834, -27834, -27578, -27322, -27322, -27067, -26811, -26555, -26555, -26299, -26043, -25788, -25788, -25532, -25276, -25276, -25020, -24764, -24508, -24508, -24252, -23996, -23740, -23740, -23484, -23228, -23228, -22972, -22716, -22460, -22460, -22204, -21948, -21948, -21692, -21436, -21180, -21180, -20924, -20668, -20668, -20411, -20155, -20155, -19899, -19643, -19643, -19387, -19130, -18874, -18874, -18618, -18362, -18361, -18105, -17849, -17849, -17592, -17336, -17336, -82616, -147895, -147895, -213175, -212918, -278454, -278198, -343478, -409013, -408757, -474036, -474036, -539316, -539315, -604595, -669875, -669874, -735154, -734897, -800433, -800177, -865456, -865456, -930735, -930735, -996014, -1061294, -1061293, -1126573, -1126572, -1191852, -1191595, -1257131, -1256874, -1322410, -1322153, -1387689, -1387432, -1452712, -1452711, -1517990, -1517990, -1583269, -1583269, -1648548, -1648292, -1713827, -1713570, -1779106, -1778849, -1778848, -1844128, -1844127, -1909406, -1909406, -1974685, -1974684, -1974428, -2039963, -2039706, -2105242, -2104985, -2104984, -2170264, -2170263, -2235542, -2235541, -2235285, -2300820, -2300563, -2366098, -2365842, -2365841, -2431120, -2431119, -2431119, -2496398, -2496397, -2496140, -2561675, -2561419, -2561418, -2561417, -2626696, -2626695, -2626439, -2691974, -2691717, -2691716, -2691715, -2756994, -2756994, -2756993, -2756736, -2756735, -2822014, -2822013, -2822012, -2821755, -2821755, -2887290, -2887033, -2887032, -2887031, -2886774, -2952309, -2952308, -2952051, -2952051, -2952050, -2951793, -2951792, -2951791, -2951534, -3017069, -3017068, -3016811, -3016810, -3016809, -3016808, -3016551, -3016551, -3016550, -3016549, -3016292, -3016291, -3016290, -3016289, -3016032, -3016031, -3016030, -3016029, -3015772, -3015771, -3015770, -3015769, -3015768, -3015511, -3015510, -3015509, -2949972, -2949971, -2949714, -2949714, -2949713, -2949712, -2949711, -2949710, -2883917, -2883916, -2883915, -2883914, -2883913, -2818376, -2818119, -2818118, -2818117, -2818116, -2752579, -2752578, -2752577, -2752576, -2687039, -2687038, -2687037, -2687037, -2621500, -2621499, -2621498, -2555961, -2555960, -2555959, -2490422, -2490421, -2490420, -2424883, -2424882, -2424881, -2359344, -2359343, -2293807, -2293806, -2293805, -2228268, -2228267, -2162730, -2162729, -2097192, -2097191, -2031654, -2031654, -1966117, -1966116, -1900579, -1900578, -1835041, -1835040, -1769504, -1769503, -1703966, -1703965, -1638428, -1638427, -1572891, -1572890, -1507353, -1441816, -1441815, -1376278, -1376278, -1310741, -1245204, -1245203, -1179667, -1114130, -1114129, -1048592, -983055, -983055, -917518, -851981, -851980, -786444, -720907, -720906, -655370, -589833, -524296, -524296, -458759, -393222, -327685, -327685, -262148, -196612, -131075, -131074, -65538, -1,};

    public static ArrayList<int[]> makePalette(double start, double rotation, double gamma, double start_hue, double end_hue, double sat, double min_sat, double max_sat, double min_light, double max_light, int n, boolean reversed) {

        if (start_hue != Double.MAX_VALUE && end_hue != Double.MAX_VALUE) {
            start = (start_hue / 360.0 - 1.) * 3.0;
            rotation = end_hue / 360.0 - start / 3.0 - 1.0;
        }

        double[] lambd = new double[n];

        for(int i = 0; i < n; i++) {
            lambd[i] = (min_light + ((max_light - min_light) / (n - 1)) * i);
        }

        double[] lambd_gamma = new double[n];

        for(int i = 0; i < n; i++) {
            lambd_gamma[i] = Math.pow(lambd[i], gamma);
        }

        double[] phi = new double[n];

        for(int i = 0; i < n; i++) {
            phi[i] = 2 * Math.PI * (start / 3.0  + 1.0 + rotation * lambd[i]);
        }

        double[] sat_vector = new double[n];

        if(sat == Double.MAX_VALUE) {
            for(int i = 0; i < n; i++) {
                sat_vector[i] = (min_sat + ((max_sat - min_sat) / (n - 1)) * i);
            }
        }
        else {
            for(int i = 0; i < n; i++) {
                sat_vector[i] = sat;
            }
        }

        double[] amp = new double[n];

        for(int i = 0; i < n; i++) {
            amp[i] = sat_vector[i] * lambd_gamma[i] * (1 - lambd_gamma[i]) / 2.0;
        }

        double[][] rot_matrix = new double[][] {{-0.14861, 1.78277}, {-0.29227, -0.90649}, {1.97294, 0.0}};

        double[][] sin_cos = new double[2][n];

        for(int i = 0; i < n; i++) {
            sin_cos[0][i] = Math.cos(phi[i]);
            sin_cos[1][i] = Math.sin(phi[i]);
        }

        double[][] rgb = new double[n][rot_matrix.length];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < rot_matrix.length; j++ ) {
                double dotp = 0;
                for(int k = 0; k < sin_cos.length; k++) {
                    dotp += rot_matrix[j][k] * sin_cos[k][i];
                }

                int indexi = i;

                if(reversed) {
                    indexi = n - 1 - i;
                }

                rgb[indexi][j] = (lambd_gamma[i] + dotp * amp[i]) * 255.0;

                //clip
                rgb[indexi][j] = rgb[indexi][j] < 0 ? 0 : rgb[indexi][j];
                rgb[indexi][j] = rgb[indexi][j] > 255 ? 255 : rgb[indexi][j];
            }
        }

        ArrayList<int[]> rgbList = new ArrayList<>(n);

        for(int i = 0; i < n; i++) {
            rgbList.add(new int[] { (int)(rgb[i][0] + 0.5), (int)(rgb[i][1] + 0.5), (int)(rgb[i][2] + 0.5)});
        }

        return rgbList;
    }
}