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
  private static Color[] default_palette = {new Color(0, 10, 20),new Color(4, 17, 38),new Color(8, 25, 56),new Color(12, 32, 75),new Color(16, 40, 93),new Color(20, 47, 111),new Color(24, 54, 130),new Color(29, 62, 148),new Color(33, 70, 166),new Color(37, 77, 185),new Color(41, 85, 203),new Color(45, 92, 221),new Color(50, 100, 240),new Color(47, 91, 222),new Color(45, 83, 204),new Color(42, 75, 186),new Color(40, 67, 168),new Color(37, 59, 150),new Color(35, 51, 133),new Color(32, 43, 115),new Color(30, 35, 97),new Color(27, 27, 79),new Color(25, 19, 61),new Color(22, 11, 43),new Color(20, 3, 26),new Color(37, 7, 25),new Color(55, 12, 25),new Color(72, 17, 24),new Color(90, 22, 24),new Color(107, 26, 23),new Color(124, 31, 23),new Color(142, 36, 22),new Color(160, 41, 22),new Color(177, 45, 21),new Color(195, 50, 21),new Color(212, 55, 20),new Color(230, 60, 20),new Color(212, 55, 19),new Color(195, 51, 18),new Color(178, 47, 17),new Color(161, 43, 16),new Color(144, 39, 15),new Color(127, 35, 14),new Color(110, 30, 13),new Color(93, 26, 12),new Color(76, 22, 11),new Color(59, 18, 10),new Color(42, 14, 9),new Color(25, 10, 9),new Color(42, 23, 8),new Color(59, 36, 7),new Color(76, 50, 6),new Color(93, 63, 6),new Color(110, 76, 5),new Color(127, 89, 4),new Color(144, 103, 3),new Color(161, 116, 3),new Color(178, 130, 2),new Color(195, 143, 1),new Color(212, 156, 0),new Color(230, 170, 0),new Color(212, 159, 0),new Color(195, 148, 1),new Color(177, 137, 2),new Color(160, 126, 3),new Color(142, 115, 4),new Color(125, 105, 4),new Color(107, 94, 5),new Color(90, 83, 6),new Color(72, 72, 7),new Color(55, 61, 8),new Color(37, 50, 9),new Color(20, 40, 10),new Color(18, 45, 9),new Color(16, 50, 8),new Color(15, 55, 7),new Color(13, 60, 6),new Color(11, 65, 5),new Color(10, 70, 5),new Color(8, 75, 4),new Color(6, 80, 3),new Color(5, 85, 2),new Color(3, 90, 1),new Color(1, 95, 0),new Color(0, 100, 0),new Color(0, 92, 0),new Color(0, 85, 1),new Color(1, 77, 2),new Color(1, 70, 3),new Color(2, 62, 4),new Color(2, 55, 4),new Color(2, 47, 5),new Color(3, 40, 6),new Color(3, 32, 7),new Color(4, 25, 8),new Color(4, 17, 9),new Color(5, 10, 10),new Color(22, 15, 11),new Color(39, 20, 13),new Color(56, 25, 15),new Color(73, 30, 16),new Color(90, 35, 18),new Color(107, 40, 20),new Color(124, 44, 21),new Color(141, 50, 23),new Color(158, 55, 25),new Color(175, 60, 26),new Color(192, 65, 28),new Color(210, 70, 30),new Color(200, 64, 31),new Color(190, 58, 33),new Color(180, 52, 35),new Color(170, 46, 36),new Color(160, 40, 38),new Color(150, 35, 40),new Color(140, 29, 41),new Color(130, 23, 43),new Color(120, 17, 45),new Color(110, 11, 46),new Color(99, 5, 48),new Color(90, 0, 50),new Color(97, 7, 55),new Color(105, 15, 61),new Color(112, 22, 67),new Color(120, 30, 73),new Color(127, 37, 79),new Color(135, 44, 85),new Color(142, 52, 90),new Color(150, 60, 96),new Color(157, 67, 102),new Color(165, 75, 108),new Color(172, 82, 114),new Color(180, 90, 120),new Color(165, 84, 113),new Color(150, 78, 106),new Color(135, 72, 100),new Color(120, 66, 93),new Color(105, 60, 86),new Color(90, 55, 80),new Color(75, 49, 73),new Color(60, 43, 66),new Color(45, 37, 60),new Color(30, 31, 53),new Color(15, 25, 46),new Color(0, 20, 40),new Color(2, 24, 53),new Color(5, 28, 66),new Color(7, 32, 80),new Color(10, 36, 93),new Color(12, 40, 106),new Color(14, 45, 119),new Color(17, 49, 133),new Color(20, 53, 146),new Color(22, 57, 160),new Color(25, 61, 173),new Color(27, 65, 186),new Color(30, 70, 200),new Color(27, 65, 185),new Color(25, 60, 170),new Color(22, 55, 155),new Color(20, 50, 140),new Color(17, 45, 125),new Color(15, 40, 110),new Color(12, 35, 95),new Color(10, 30, 80),new Color(7, 25, 65),new Color(5, 20, 50),new Color(2, 14, 35),};
  private static Color[] spectrum_palette = {new Color(0, 24, 255),new Color(9, 22, 255),new Color(18, 21, 255),new Color(27, 20, 255),new Color(36, 19, 255),new Color(45, 18, 255),new Color(55, 17, 255),new Color(64, 16, 255),new Color(73, 15, 255),new Color(82, 14, 255),new Color(91, 13, 255),new Color(101, 11, 255),new Color(110, 10, 255),new Color(119, 9, 255),new Color(128, 8, 255),new Color(137, 7, 255),new Color(146, 6, 255),new Color(156, 5, 255),new Color(165, 4, 255),new Color(174, 3, 255),new Color(183, 2, 255),new Color(192, 1, 255),new Color(202, 0, 255),new Color(204, 0, 246),new Color(207, 0, 238),new Color(209, 0, 230),new Color(212, 0, 222),new Color(214, 0, 213),new Color(217, 0, 205),new Color(219, 0, 197),new Color(222, 0, 189),new Color(224, 0, 180),new Color(227, 0, 172),new Color(229, 0, 164),new Color(232, 0, 156),new Color(234, 0, 147),new Color(237, 0, 139),new Color(239, 0, 131),new Color(242, 0, 123),new Color(244, 0, 114),new Color(247, 0, 106),new Color(249, 0, 98),new Color(252, 0, 90),new Color(255, 0, 82),new Color(255, 6, 78),new Color(255, 12, 74),new Color(255, 19, 70),new Color(255, 25, 66),new Color(255, 31, 62),new Color(255, 38, 58),new Color(255, 44, 54),new Color(255, 50, 50),new Color(255, 57, 46),new Color(255, 63, 42),new Color(255, 69, 39),new Color(255, 76, 35),new Color(255, 82, 31),new Color(255, 88, 27),new Color(255, 95, 23),new Color(255, 101, 19),new Color(255, 107, 15),new Color(255, 114, 11),new Color(255, 120, 7),new Color(255, 126, 3),new Color(255, 133, 0),new Color(250, 138, 0),new Color(245, 144, 0),new Color(240, 149, 0),new Color(236, 155, 0),new Color(231, 160, 0),new Color(226, 166, 0),new Color(221, 171, 0),new Color(217, 177, 0),new Color(212, 182, 0),new Color(207, 188, 0),new Color(203, 194, 0),new Color(198, 199, 0),new Color(193, 205, 0),new Color(188, 210, 0),new Color(184, 216, 0),new Color(179, 221, 0),new Color(174, 227, 0),new Color(169, 232, 0),new Color(165, 238, 0),new Color(160, 243, 0),new Color(155, 249, 0),new Color(151, 255, 0),new Color(143, 255, 3),new Color(136, 255, 7),new Color(129, 255, 10),new Color(122, 255, 14),new Color(115, 255, 17),new Color(107, 255, 21),new Color(100, 255, 25),new Color(93, 255, 28),new Color(86, 255, 32),new Color(79, 255, 35),new Color(71, 255, 39),new Color(64, 255, 42),new Color(57, 255, 46),new Color(50, 255, 50),new Color(43, 255, 53),new Color(35, 255, 57),new Color(28, 255, 60),new Color(21, 255, 64),new Color(14, 255, 67),new Color(7, 255, 71),new Color(0, 255, 75),new Color(0, 252, 83),new Color(0, 250, 92),new Color(0, 248, 100),new Color(0, 246, 109),new Color(0, 244, 117),new Color(0, 241, 126),new Color(0, 239, 135),new Color(0, 237, 143),new Color(0, 235, 152),new Color(0, 233, 160),new Color(0, 230, 169),new Color(0, 228, 177),new Color(0, 226, 186),new Color(0, 224, 195),new Color(0, 222, 203),new Color(0, 219, 212),new Color(0, 217, 220),new Color(0, 215, 229),new Color(0, 213, 237),new Color(0, 211, 246),new Color(0, 209, 255),new Color(0, 200, 255),new Color(0, 192, 255),new Color(0, 183, 255),new Color(0, 175, 255),new Color(0, 166, 255),new Color(0, 158, 255),new Color(0, 150, 255),new Color(0, 141, 255),new Color(0, 133, 255),new Color(0, 124, 255),new Color(0, 116, 255),new Color(0, 108, 255),new Color(0, 99, 255),new Color(0, 91, 255),new Color(0, 82, 255),new Color(0, 74, 255),new Color(0, 66, 255),new Color(0, 57, 255),new Color(0, 49, 255),new Color(0, 40, 255),new Color(0, 32, 255),};
  private static Color[] alternative_palette = {new Color(0, 0, 191),new Color(8, 0, 195),new Color(16, 0, 199),new Color(24, 0, 203),new Color(32, 0, 208),new Color(41, 0, 212),new Color(49, 0, 216),new Color(57, 0, 220),new Color(65, 0, 225),new Color(73, 0, 229),new Color(82, 0, 233),new Color(90, 0, 237),new Color(98, 0, 242),new Color(106, 0, 246),new Color(114, 0, 250),new Color(123, 0, 255),new Color(128, 0, 240),new Color(133, 0, 226),new Color(138, 0, 211),new Color(144, 0, 197),new Color(149, 0, 182),new Color(154, 0, 168),new Color(159, 0, 153),new Color(165, 0, 139),new Color(173, 18, 115),new Color(181, 36, 92),new Color(189, 54, 69),new Color(197, 72, 46),new Color(205, 90, 23),new Color(214, 109, 0),new Color(222, 127, 0),new Color(230, 145, 0),new Color(238, 163, 0),new Color(246, 181, 0),new Color(255, 200, 0),new Color(252, 211, 0),new Color(250, 222, 0),new Color(247, 233, 0),new Color(245, 244, 0),new Color(243, 255, 0),new Color(238, 229, 0),new Color(234, 204, 0),new Color(230, 178, 0),new Color(226, 153, 0),new Color(222, 127, 0),new Color(217, 102, 0),new Color(213, 76, 0),new Color(209, 51, 0),new Color(205, 25, 0),new Color(201, 0, 0),new Color(196, 0, 16),new Color(192, 0, 32),new Color(188, 0, 48),new Color(184, 0, 64),new Color(180, 0, 79),new Color(176, 0, 95),new Color(172, 0, 112),new Color(176, 0, 127),new Color(180, 0, 143),new Color(184, 0, 159),new Color(188, 0, 175),new Color(192, 0, 191),new Color(196, 0, 207),new Color(200, 0, 223),new Color(204, 0, 239),new Color(204, 2, 242),new Color(205, 4, 245),new Color(206, 6, 248),new Color(207, 8, 251),new Color(208, 11, 255),new Color(211, 21, 249),new Color(215, 32, 244),new Color(219, 42, 239),new Color(223, 53, 234),new Color(227, 63, 229),new Color(231, 74, 224),new Color(235, 85, 218),new Color(239, 95, 213),new Color(243, 106, 208),new Color(247, 116, 203),new Color(251, 127, 198),new Color(255, 138, 193),new Color(244, 144, 190),new Color(233, 150, 187),new Color(222, 157, 184),new Color(211, 163, 181),new Color(200, 170, 178),new Color(181, 180, 182),new Color(163, 191, 187),new Color(145, 201, 192),new Color(127, 212, 197),new Color(109, 223, 202),new Color(91, 233, 207),new Color(73, 244, 212),new Color(55, 255, 217),new Color(44, 245, 219),new Color(33, 235, 222),new Color(21, 225, 225),new Color(11, 215, 228),new Color(0, 206, 231),new Color(0, 189, 235),new Color(0, 173, 240),new Color(0, 156, 245),new Color(0, 140, 250),new Color(0, 124, 255),new Color(0, 107, 241),new Color(0, 91, 227),new Color(0, 75, 214),new Color(0, 58, 200),new Color(0, 42, 186),new Color(0, 26, 173),new Color(0, 36, 159),new Color(0, 46, 145),new Color(0, 56, 132),new Color(0, 66, 118),new Color(0, 76, 105),new Color(0, 86, 91),new Color(0, 96, 78),new Color(0, 106, 64),new Color(0, 116, 51),new Color(0, 125, 65),new Color(0, 135, 80),new Color(0, 145, 94),new Color(0, 155, 109),new Color(0, 165, 123),new Color(0, 175, 138),new Color(0, 185, 153),new Color(0, 195, 167),new Color(0, 205, 182),new Color(0, 215, 196),new Color(0, 225, 211),new Color(0, 235, 226),new Color(6, 239, 231),new Color(12, 243, 237),new Color(18, 247, 243),new Color(24, 251, 249),new Color(30, 255, 255),new Color(44, 245, 255),new Color(59, 236, 255),new Color(74, 227, 255),new Color(89, 218, 255),new Color(104, 209, 255),new Color(119, 200, 255),new Color(134, 191, 255),new Color(149, 190, 255),new Color(164, 189, 255),new Color(179, 189, 255),new Color(194, 188, 255),new Color(209, 187, 255),new Color(224, 187, 255),new Color(239, 186, 255),new Color(254, 186, 255),new Color(251, 185, 255),new Color(249, 185, 255),new Color(246, 185, 255),new Color(244, 185, 255),new Color(242, 185, 255),new Color(240, 184, 252),new Color(239, 184, 250),new Color(237, 184, 248),new Color(236, 184, 246),new Color(235, 184, 244),new Color(228, 175, 233),new Color(222, 167, 222),new Color(216, 158, 211),new Color(210, 150, 200),new Color(204, 142, 189),new Color(197, 133, 178),new Color(191, 125, 167),new Color(185, 117, 156),new Color(179, 108, 145),new Color(173, 100, 134),new Color(167, 92, 123),new Color(160, 83, 112),new Color(154, 75, 101),new Color(148, 67, 90),new Color(142, 58, 78),new Color(136, 50, 67),new Color(130, 42, 57),new Color(123, 33, 68),new Color(117, 25, 80),new Color(111, 16, 92),new Color(105, 8, 104),new Color(99, 0, 116),new Color(92, 8, 127),new Color(86, 16, 139),new Color(80, 24, 151),new Color(74, 32, 163),new Color(68, 40, 174),new Color(62, 48, 186),new Color(56, 56, 198),new Color(50, 64, 210),new Color(43, 72, 204),new Color(37, 80, 199),new Color(31, 88, 193),new Color(25, 96, 188),new Color(18, 104, 183),new Color(12, 112, 177),new Color(6, 120, 172),new Color(0, 128, 167),new Color(5, 135, 161),new Color(10, 143, 156),new Color(16, 151, 150),new Color(21, 159, 145),new Color(26, 167, 139),new Color(31, 175, 134),new Color(37, 183, 129),new Color(42, 191, 123),new Color(48, 199, 118),new Color(53, 207, 112),new Color(58, 215, 107),new Color(64, 223, 102),new Color(69, 231, 110),new Color(75, 239, 119),new Color(80, 247, 128),new Color(86, 255, 137),new Color(91, 247, 145),new Color(96, 240, 154),new Color(102, 233, 163),new Color(107, 226, 172),new Color(112, 219, 181),new Color(118, 212, 190),new Color(123, 205, 199),new Color(128, 198, 208),new Color(134, 191, 217),new Color(139, 183, 211),new Color(144, 176, 205),new Color(150, 169, 199),new Color(155, 162, 193),new Color(160, 155, 187),new Color(166, 148, 181),new Color(171, 141, 175),new Color(177, 134, 169),new Color(182, 127, 163),new Color(187, 120, 157),new Color(193, 113, 151),new Color(198, 106, 145),new Color(204, 99, 140),new Color(189, 92, 134),new Color(175, 85, 128),new Color(160, 78, 122),new Color(146, 71, 116),new Color(131, 64, 110),new Color(117, 57, 105),new Color(105, 51, 113),new Color(93, 45, 122),new Color(81, 39, 130),new Color(70, 34, 139),new Color(58, 28, 148),new Color(46, 22, 156),new Color(35, 17, 165),new Color(23, 11, 173),new Color(11, 5, 182),};
  private static Color[] alternative2_palette = {new Color(161, 36, 32),new Color(150, 34, 30),new Color(139, 32, 28),new Color(128, 30, 26),new Color(118, 29, 24),new Color(107, 27, 22),new Color(96, 25, 20),new Color(85, 23, 18),new Color(75, 22, 16),new Color(64, 20, 14),new Color(53, 18, 12),new Color(42, 16, 10),new Color(32, 15, 8),new Color(47, 31, 23),new Color(62, 47, 38),new Color(77, 63, 53),new Color(92, 79, 69),new Color(107, 95, 84),new Color(122, 110, 99),new Color(138, 126, 114),new Color(153, 143, 130),new Color(168, 159, 145),new Color(183, 175, 160),new Color(198, 191, 175),new Color(214, 207, 191),new Color(213, 205, 185),new Color(213, 203, 180),new Color(212, 201, 175),new Color(212, 199, 169),new Color(211, 197, 164),new Color(211, 195, 159),new Color(211, 193, 153),new Color(210, 191, 148),new Color(210, 189, 143),new Color(209, 187, 137),new Color(209, 185, 132),new Color(209, 184, 127),new Color(205, 178, 120),new Color(201, 172, 114),new Color(197, 167, 107),new Color(194, 161, 101),new Color(190, 156, 94),new Color(186, 150, 88),new Color(182, 144, 81),new Color(179, 139, 75),new Color(175, 133, 68),new Color(171, 128, 62),new Color(167, 122, 55),new Color(164, 117, 49),new Color(163, 110, 47),new Color(163, 103, 46),new Color(163, 96, 44),new Color(163, 90, 43),new Color(162, 83, 41),new Color(162, 76, 40),new Color(162, 69, 39),new Color(162, 63, 37),new Color(161, 56, 36),new Color(161, 49, 34),new Color(161, 42, 33),};
  private static Color[] alternative3_palette = {new Color(214, 198, 146),new Color(216, 200, 150),new Color(218, 202, 155),new Color(220, 204, 160),new Color(223, 207, 164),new Color(225, 209, 169),new Color(227, 211, 174),new Color(229, 213, 178),new Color(232, 216, 183),new Color(234, 218, 188),new Color(236, 220, 192),new Color(238, 222, 197),new Color(241, 225, 202),new Color(228, 215, 190),new Color(216, 205, 178),new Color(204, 195, 167),new Color(192, 185, 155),new Color(180, 175, 143),new Color(168, 165, 132),new Color(156, 155, 120),new Color(144, 145, 108),new Color(132, 135, 97),new Color(120, 125, 85),new Color(108, 114, 73),new Color(96, 105, 62),new Color(98, 110, 65),new Color(101, 115, 68),new Color(104, 121, 72),new Color(107, 126, 75),new Color(109, 132, 78),new Color(112, 137, 82),new Color(115, 142, 85),new Color(118, 148, 88),new Color(120, 153, 92),new Color(123, 159, 95),new Color(126, 164, 98),new Color(129, 170, 102),new Color(125, 161, 96),new Color(121, 153, 91),new Color(118, 144, 86),new Color(114, 136, 81),new Color(111, 127, 76),new Color(107, 119, 71),new Color(103, 111, 66),new Color(100, 102, 61),new Color(96, 94, 56),new Color(93, 85, 51),new Color(89, 77, 46),new Color(86, 69, 41),new Color(96, 79, 49),new Color(107, 90, 58),new Color(118, 101, 67),new Color(128, 112, 76),new Color(139, 122, 84),new Color(150, 133, 93),new Color(160, 144, 102),new Color(171, 155, 111),new Color(182, 165, 119),new Color(192, 176, 128),new Color(203, 187, 137),};
  private static Color[] alternative4_palette = {new Color(12, 0, 0),new Color(16, 4, 4),new Color(21, 8, 8),new Color(25, 12, 12),new Color(30, 16, 16),new Color(35, 19, 19),new Color(39, 23, 23),new Color(44, 27, 27),new Color(49, 31, 31),new Color(53, 35, 35),new Color(58, 39, 39),new Color(63, 43, 43),new Color(67, 47, 47),new Color(72, 51, 51),new Color(77, 56, 56),new Color(76, 52, 52),new Color(75, 48, 48),new Color(75, 45, 45),new Color(74, 41, 41),new Color(74, 38, 38),new Color(73, 34, 34),new Color(73, 31, 31),new Color(72, 27, 27),new Color(71, 23, 23),new Color(71, 20, 20),new Color(70, 16, 16),new Color(70, 13, 13),new Color(69, 9, 9),new Color(69, 6, 6),new Color(74, 9, 9),new Color(80, 13, 13),new Color(85, 16, 16),new Color(91, 20, 20),new Color(97, 23, 23),new Color(102, 26, 27),new Color(108, 30, 30),new Color(114, 33, 34),new Color(119, 37, 38),new Color(125, 40, 41),new Color(131, 44, 45),new Color(136, 47, 48),new Color(142, 51, 52),new Color(148, 55, 56),new Color(155, 65, 66),new Color(162, 75, 76),new Color(170, 85, 86),new Color(177, 95, 96),new Color(184, 105, 107),new Color(192, 114, 117),new Color(199, 124, 127),new Color(206, 135, 137),new Color(214, 144, 147),new Color(221, 154, 158),new Color(228, 164, 168),new Color(236, 174, 178),new Color(243, 184, 188),new Color(251, 195, 199),new Color(233, 181, 184),new Color(216, 167, 170),new Color(199, 153, 156),new Color(182, 139, 142),new Color(165, 125, 127),new Color(148, 111, 113),new Color(131, 97, 99),new Color(114, 83, 85),new Color(97, 69, 71),new Color(80, 55, 56),new Color(63, 41, 42),new Color(46, 27, 28),new Color(29, 13, 14),};
  private static Color[] alternative5_palette = {new Color(186, 44, 4),new Color(188, 62, 28),new Color(190, 81, 52),new Color(192, 100, 77),new Color(194, 119, 101),new Color(196, 138, 125),new Color(198, 157, 150),new Color(200, 176, 174),new Color(202, 195, 198),new Color(204, 214, 223),new Color(190, 206, 206),new Color(176, 199, 189),new Color(162, 192, 172),new Color(148, 185, 155),new Color(134, 178, 139),new Color(120, 171, 122),new Color(106, 164, 105),new Color(92, 157, 88),new Color(78, 150, 71),new Color(65, 143, 55),new Color(87, 155, 51),new Color(109, 167, 48),new Color(131, 179, 44),new Color(153, 191, 41),new Color(175, 203, 38),new Color(197, 215, 34),new Color(219, 227, 31),new Color(242, 239, 28),new Color(218, 213, 29),new Color(195, 187, 31),new Color(172, 161, 32),new Color(149, 135, 34),new Color(126, 109, 35),new Color(103, 83, 37),new Color(80, 57, 38),new Color(57, 31, 40),new Color(71, 56, 45),new Color(86, 82, 51),new Color(100, 108, 56),new Color(115, 134, 62),new Color(130, 159, 68),new Color(144, 185, 73),new Color(159, 211, 79),new Color(174, 237, 85),new Color(156, 232, 101),new Color(139, 228, 117),new Color(122, 224, 133),new Color(104, 220, 149),new Color(87, 216, 165),new Color(69, 212, 181),new Color(52, 208, 197),new Color(35, 204, 213),new Color(18, 200, 230),new Color(40, 176, 232),new Color(63, 153, 235),new Color(86, 129, 237),new Color(109, 106, 240),new Color(132, 82, 242),new Color(155, 59, 245),new Color(154, 63, 230),new Color(154, 67, 216),new Color(153, 71, 201),new Color(153, 75, 187),new Color(152, 79, 172),new Color(152, 84, 158),new Color(149, 83, 148),new Color(146, 82, 139),new Color(143, 81, 129),new Color(141, 81, 120),new Color(138, 80, 111),new Color(135, 79, 101),new Color(133, 79, 92),new Color(130, 78, 83),new Color(127, 77, 73),new Color(124, 76, 64),new Color(122, 76, 55),new Color(119, 75, 45),new Color(116, 74, 36),new Color(114, 74, 27),new Color(122, 80, 36),new Color(130, 87, 46),new Color(138, 93, 55),new Color(146, 100, 65),new Color(154, 106, 74),new Color(162, 113, 84),new Color(170, 119, 93),new Color(178, 126, 103),new Color(186, 132, 112),new Color(194, 139, 122),new Color(202, 145, 131),new Color(210, 152, 141),new Color(218, 159, 151),new Color(214, 150, 147),new Color(210, 142, 144),new Color(206, 134, 141),new Color(202, 126, 137),new Color(198, 117, 134),new Color(195, 109, 131),new Color(191, 101, 127),new Color(187, 93, 124),new Color(183, 84, 121),new Color(179, 76, 117),new Color(175, 68, 114),new Color(172, 60, 111),new Color(173, 58, 103),new Color(174, 57, 95),new Color(175, 56, 88),new Color(176, 55, 80),new Color(177, 54, 72),new Color(178, 53, 65),new Color(179, 52, 57),new Color(180, 50, 49),new Color(181, 49, 42),new Color(182, 48, 34),new Color(183, 47, 26),new Color(184, 46, 19),new Color(185, 45, 11),};
  private static Color[] alternative6_palette = {new Color(104, 225, 252),new Color(117, 225, 241),new Color(131, 226, 231),new Color(145, 226, 221),new Color(158, 227, 210),new Color(172, 227, 200),new Color(186, 228, 190),new Color(200, 228, 180),new Color(213, 229, 169),new Color(227, 229, 159),new Color(241, 230, 149),new Color(255, 231, 139),new Color(234, 206, 134),new Color(213, 181, 130),new Color(192, 156, 126),new Color(172, 131, 122),new Color(151, 106, 118),new Color(130, 81, 114),new Color(110, 57, 110),new Color(111, 69, 113),new Color(112, 82, 117),new Color(113, 94, 121),new Color(114, 107, 124),new Color(115, 120, 128),new Color(116, 132, 132),new Color(117, 145, 136),new Color(118, 158, 139),new Color(119, 170, 143),new Color(120, 183, 147),new Color(121, 196, 151),new Color(111, 186, 134),new Color(102, 177, 117),new Color(93, 167, 100),new Color(84, 158, 84),new Color(74, 149, 67),new Color(65, 139, 50),new Color(56, 130, 33),new Color(47, 121, 17),new Color(51, 122, 25),new Color(55, 124, 34),new Color(59, 126, 42),new Color(64, 128, 51),new Color(68, 130, 59),new Color(72, 132, 68),new Color(76, 134, 76),new Color(81, 136, 85),new Color(93, 138, 83),new Color(105, 141, 81),new Color(117, 143, 80),new Color(130, 146, 78),new Color(142, 148, 77),new Color(154, 151, 75),new Color(167, 154, 74),new Color(166, 150, 70),new Color(166, 147, 66),new Color(166, 144, 63),new Color(165, 141, 59),new Color(165, 138, 56),new Color(165, 135, 52),new Color(165, 132, 49),new Color(164, 129, 45),new Color(164, 126, 42),new Color(164, 123, 38),new Color(164, 120, 35),new Color(163, 117, 31),new Color(163, 114, 28),new Color(163, 111, 24),new Color(163, 108, 21),new Color(161, 107, 22),new Color(159, 106, 24),new Color(157, 106, 25),new Color(155, 105, 27),new Color(153, 104, 29),new Color(151, 104, 30),new Color(150, 103, 32),new Color(148, 102, 34),new Color(146, 102, 35),new Color(144, 101, 37),new Color(142, 100, 39),new Color(140, 100, 40),new Color(138, 99, 42),new Color(137, 99, 44),new Color(126, 113, 44),new Color(115, 127, 44),new Color(105, 141, 45),new Color(94, 155, 45),new Color(84, 170, 45),new Color(73, 184, 46),new Color(63, 198, 46),new Color(52, 212, 46),new Color(42, 227, 47),new Color(50, 218, 56),new Color(59, 210, 65),new Color(68, 202, 74),new Color(77, 194, 83),new Color(86, 186, 92),new Color(95, 178, 101),new Color(104, 170, 110),new Color(112, 161, 120),new Color(121, 153, 129),new Color(130, 145, 138),new Color(139, 137, 147),new Color(148, 129, 156),new Color(157, 121, 165),new Color(166, 113, 174),new Color(175, 105, 184),new Color(166, 113, 165),new Color(157, 121, 147),new Color(148, 129, 129),new Color(139, 137, 111),new Color(130, 145, 93),new Color(121, 153, 75),new Color(112, 161, 57),new Color(103, 169, 39),new Color(103, 173, 56),new Color(103, 178, 74),new Color(103, 183, 92),new Color(103, 187, 110),new Color(103, 192, 127),new Color(103, 197, 145),new Color(103, 201, 163),new Color(103, 206, 181),new Color(103, 211, 198),new Color(103, 215, 216),new Color(103, 220, 234),};
  private static Color[] alternative7_palette = {new Color(37, 215, 240),new Color(51, 198, 229),new Color(66, 181, 218),new Color(81, 165, 207),new Color(96, 148, 196),new Color(110, 132, 185),new Color(125, 115, 174),new Color(140, 98, 163),new Color(155, 82, 152),new Color(169, 65, 141),new Color(184, 49, 130),new Color(199, 32, 118),new Color(214, 16, 108),new Color(215, 27, 99),new Color(217, 39, 91),new Color(218, 51, 83),new Color(220, 62, 74),new Color(221, 74, 66),new Color(223, 86, 57),new Color(224, 97, 49),new Color(226, 109, 41),new Color(228, 121, 33),new Color(221, 110, 29),new Color(215, 99, 25),new Color(209, 88, 21),new Color(202, 78, 18),new Color(196, 67, 14),new Color(190, 56, 10),new Color(184, 46, 7),new Color(163, 44, 8),new Color(143, 42, 9),new Color(123, 40, 10),new Color(102, 38, 11),new Color(82, 36, 13),new Color(61, 34, 14),new Color(41, 32, 15),new Color(21, 29, 16),new Color(1, 28, 18),new Color(3, 38, 42),new Color(5, 49, 67),new Color(8, 60, 92),new Color(10, 71, 117),new Color(12, 81, 142),new Color(15, 92, 167),new Color(17, 103, 192),new Color(20, 114, 217),new Color(17, 118, 196),new Color(15, 123, 175),new Color(12, 128, 154),new Color(10, 133, 134),new Color(7, 138, 113),new Color(5, 143, 92),new Color(2, 148, 71),new Color(0, 153, 51),new Color(0, 136, 51),new Color(0, 119, 51),new Color(0, 102, 51),new Color(0, 85, 51),new Color(0, 68, 51),new Color(0, 51, 51),new Color(15, 66, 49),new Color(30, 81, 48),new Color(45, 97, 46),new Color(60, 112, 45),new Color(75, 128, 43),new Color(90, 143, 42),new Color(105, 159, 40),new Color(120, 174, 39),new Color(135, 190, 37),new Color(150, 205, 36),new Color(165, 221, 35),new Color(164, 211, 53),new Color(163, 201, 71),new Color(162, 191, 90),new Color(161, 181, 108),new Color(160, 171, 126),new Color(159, 161, 145),new Color(158, 151, 163),new Color(157, 141, 181),new Color(156, 131, 200),new Color(155, 121, 218),new Color(154, 111, 236),new Color(153, 102, 255),new Color(154, 111, 245),new Color(155, 120, 236),new Color(156, 129, 227),new Color(158, 139, 217),new Color(159, 148, 208),new Color(160, 157, 199),new Color(162, 167, 189),new Color(163, 176, 180),new Color(164, 185, 171),new Color(166, 195, 162),new Color(168, 197, 168),new Color(171, 200, 175),new Color(174, 203, 181),new Color(176, 205, 188),new Color(179, 208, 194),new Color(182, 211, 201),new Color(184, 213, 207),new Color(187, 216, 214),new Color(190, 219, 220),new Color(193, 222, 227),new Color(170, 221, 228),new Color(148, 220, 230),new Color(126, 219, 232),new Color(103, 218, 234),new Color(81, 217, 236),new Color(59, 216, 238),};
  private static Color[] greenwhite_palette = {new Color(40, 70, 10),new Color(40, 82, 10),new Color(40, 95, 10),new Color(40, 107, 10),new Color(40, 120, 10),new Color(40, 132, 10),new Color(40, 145, 10),new Color(40, 157, 10),new Color(40, 170, 10),new Color(46, 179, 16),new Color(53, 188, 23),new Color(60, 198, 30),new Color(66, 207, 36),new Color(73, 217, 43),new Color(80, 226, 50),new Color(86, 236, 56),new Color(93, 245, 63),new Color(100, 255, 70),new Color(125, 255, 100),new Color(151, 255, 131),new Color(177, 255, 162),new Color(203, 255, 193),new Color(229, 255, 224),new Color(255, 255, 255),new Color(228, 231, 224),new Color(201, 208, 193),new Color(174, 185, 163),new Color(147, 162, 132),new Color(120, 139, 101),new Color(93, 116, 71),new Color(66, 93, 40),};
  private static Color[] blue_palette = {new Color(0, 0, 64),new Color(0, 0, 79),new Color(0, 0, 95),new Color(0, 0, 111),new Color(0, 0, 127),new Color(0, 0, 143),new Color(0, 0, 159),new Color(0, 0, 175),new Color(0, 0, 191),new Color(0, 0, 207),new Color(0, 0, 223),new Color(0, 0, 239),new Color(0, 0, 255),new Color(0, 21, 255),new Color(0, 42, 255),new Color(0, 63, 255),new Color(0, 85, 255),new Color(0, 106, 255),new Color(0, 127, 255),new Color(0, 148, 255),new Color(0, 170, 255),new Color(0, 191, 255),new Color(0, 212, 255),new Color(0, 233, 255),new Color(0, 255, 255),new Color(12, 255, 255),new Color(25, 255, 255),new Color(38, 255, 255),new Color(51, 255, 255),new Color(64, 255, 255),new Color(76, 255, 255),new Color(89, 255, 255),new Color(102, 255, 255),new Color(115, 255, 255),new Color(128, 255, 255),new Color(122, 244, 255),new Color(117, 233, 255),new Color(112, 223, 255),new Color(106, 212, 255),new Color(101, 202, 255),new Color(96, 191, 255),new Color(90, 180, 255),new Color(85, 170, 255),new Color(80, 159, 255),new Color(74, 149, 255),new Color(69, 138, 255),new Color(64, 128, 255),new Color(59, 118, 241),new Color(54, 109, 227),new Color(50, 100, 214),new Color(45, 91, 200),new Color(41, 82, 186),new Color(36, 73, 173),new Color(32, 64, 159),new Color(27, 54, 145),new Color(22, 45, 132),new Color(18, 36, 118),new Color(13, 27, 104),new Color(9, 18, 91),new Color(4, 9, 77),};
  private static Color[] grayscale_palette = {new Color(255, 255, 255),new Color(243, 243, 243),new Color(231, 231, 231),new Color(220, 220, 220),new Color(208, 208, 208),new Color(197, 197, 197),new Color(185, 185, 185),new Color(173, 173, 173),new Color(162, 162, 162),new Color(150, 150, 150),new Color(139, 139, 139),new Color(127, 127, 127),new Color(115, 115, 115),new Color(104, 104, 104),new Color(92, 92, 92),new Color(81, 81, 81),new Color(69, 69, 69),new Color(57, 57, 57),new Color(46, 46, 46),new Color(34, 34, 34),new Color(23, 23, 23),new Color(11, 11, 11),new Color(0, 0, 0),new Color(12, 12, 12),new Color(24, 24, 24),new Color(36, 36, 36),new Color(48, 48, 48),new Color(60, 60, 60),new Color(72, 72, 72),new Color(85, 85, 85),new Color(97, 97, 97),new Color(109, 109, 109),new Color(121, 121, 121),new Color(133, 133, 133),new Color(145, 145, 145),new Color(157, 157, 157),new Color(170, 170, 170),new Color(182, 182, 182),new Color(194, 194, 194),new Color(206, 206, 206),new Color(218, 218, 218),new Color(230, 230, 230),new Color(242, 242, 242),};
  private static Color[] earthsky_palette = {new Color(255, 255, 255),new Color(255, 248, 224),new Color(255, 242, 194),new Color(255, 236, 163),new Color(255, 230, 133),new Color(255, 224, 103),new Color(255, 218, 72),new Color(255, 212, 42),new Color(255, 206, 12),new Color(243, 189, 12),new Color(232, 172, 12),new Color(221, 156, 12),new Color(210, 139, 13),new Color(198, 122, 13),new Color(187, 106, 13),new Color(176, 89, 13),new Color(165, 73, 14),new Color(156, 66, 19),new Color(147, 60, 25),new Color(139, 53, 30),new Color(130, 47, 36),new Color(121, 41, 41),new Color(113, 34, 47),new Color(104, 28, 52),new Color(96, 22, 58),new Color(88, 20, 65),new Color(81, 18, 72),new Color(74, 17, 79),new Color(67, 15, 86),new Color(60, 13, 93),new Color(53, 12, 100),new Color(46, 10, 107),new Color(39, 9, 114),new Color(34, 11, 122),new Color(29, 13, 130),new Color(24, 15, 138),new Color(19, 17, 146),new Color(14, 19, 154),new Color(9, 21, 162),new Color(4, 23, 170),new Color(0, 25, 178),new Color(1, 35, 187),new Color(3, 46, 197),new Color(4, 56, 206),new Color(6, 67, 216),new Color(7, 77, 226),new Color(9, 88, 235),new Color(10, 98, 245),new Color(12, 109, 255),new Color(42, 127, 255),new Color(72, 145, 255),new Color(103, 163, 255),new Color(133, 182, 255),new Color(163, 200, 255),new Color(194, 218, 255),new Color(224, 236, 255),};
  private static Color[] hotcold_palette = {new Color(255, 255, 255),new Color(226, 237, 255),new Color(198, 220, 255),new Color(169, 203, 255),new Color(141, 186, 255),new Color(112, 169, 255),new Color(84, 152, 255),new Color(55, 135, 255),new Color(27, 118, 255),new Color(26, 113, 245),new Color(25, 109, 236),new Color(24, 104, 226),new Color(23, 100, 217),new Color(22, 96, 208),new Color(21, 91, 198),new Color(20, 87, 189),new Color(19, 83, 180),new Color(21, 80, 169),new Color(24, 77, 158),new Color(26, 75, 147),new Color(29, 72, 137),new Color(32, 69, 126),new Color(34, 67, 115),new Color(37, 64, 104),new Color(40, 62, 94),new Color(46, 59, 92),new Color(53, 56, 91),new Color(60, 53, 89),new Color(67, 51, 88),new Color(73, 48, 87),new Color(80, 45, 85),new Color(87, 42, 84),new Color(94, 40, 83),new Color(104, 37, 91),new Color(115, 34, 99),new Color(126, 32, 107),new Color(137, 29, 115),new Color(147, 26, 123),new Color(158, 24, 131),new Color(169, 21, 139),new Color(180, 19, 147),new Color(189, 20, 154),new Color(198, 21, 162),new Color(208, 22, 170),new Color(217, 23, 178),new Color(226, 24, 185),new Color(236, 25, 193),new Color(245, 26, 201),new Color(255, 27, 209),new Color(255, 55, 214),new Color(255, 84, 220),new Color(255, 112, 226),new Color(255, 141, 232),new Color(255, 169, 237),new Color(255, 198, 243),new Color(255, 226, 249),};
  private static Color[] fire_palette = {new Color(0, 0, 0),new Color(26, 0, 0),new Color(53, 0, 0),new Color(80, 0, 0),new Color(107, 0, 0),new Color(133, 0, 0),new Color(160, 0, 0),new Color(187, 0, 0),new Color(214, 0, 0),new Color(219, 5, 0),new Color(224, 11, 0),new Color(229, 16, 0),new Color(234, 22, 0),new Color(239, 28, 0),new Color(244, 33, 0),new Color(249, 39, 0),new Color(255, 45, 0),new Color(255, 51, 0),new Color(255, 58, 0),new Color(255, 65, 0),new Color(255, 72, 0),new Color(255, 79, 0),new Color(255, 86, 0),new Color(255, 93, 0),new Color(255, 100, 0),new Color(255, 106, 0),new Color(255, 113, 0),new Color(255, 120, 0),new Color(255, 127, 0),new Color(255, 134, 0),new Color(255, 141, 0),new Color(255, 148, 0),new Color(255, 155, 0),new Color(255, 161, 0),new Color(255, 168, 0),new Color(255, 175, 0),new Color(255, 182, 0),new Color(255, 189, 0),new Color(255, 196, 0),new Color(255, 203, 0),new Color(255, 210, 0),new Color(255, 215, 5),new Color(255, 221, 10),new Color(255, 226, 15),new Color(255, 232, 20),new Color(255, 238, 25),new Color(255, 243, 30),new Color(255, 249, 35),new Color(255, 255, 41),new Color(223, 223, 35),new Color(191, 191, 30),new Color(159, 159, 25),new Color(127, 127, 20),new Color(95, 95, 15),new Color(63, 63, 10),new Color(31, 31, 5),};
  

     public Palette(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, int in_coloring_algorithm, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, boolean init_val, double[] initial_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, in_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, coefficients);

        /*int[][] colors = {
            {12,  0,   10,  20},
            {12,  50, 100, 240},
            {12,  20,   3,  26},
            {12, 230,  60,  20},
            {12,  25,  10,   9},
            {12, 230, 170,   0},
            {12,  20,  40,  10},
            {12,   0, 100,   0},
            {12,   5,  10,  10},
            {12, 210,  70,  30},
            {12,  90,   0,  50},
            {12, 180,  90, 120},
            {12,   0,  20,  40},
            {12,  30,  70, 200},};*/
        
        
        
        /*for(int i = 0; i < 7; i++) {
           System.out.println("{" + 8 + ", " + palette.getColor((i * 8) / 56.0).getRed() + ", " + palette.getColor((i * 8) / 56.0).getGreen() + ", " + palette.getColor((i * 8) / 56.0).getBlue() + "},"); 
        }*/
  
   
       /*
       int n = 0;
        for (int i = 0; i < colors.length; i++) { // get the number of all colors
            n += colors[i][0];
        }
        //palette = new Color[n]; // allocate pallete

        n = 0;
        int red, green, blue;
        System.out.print("{");
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            
           
            int k;
            double j;
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {// linear interpolation of RGB values
                red = (int)(c1[1] + (c2[1] - c1[1]) * j);
                green = (int)(c1[2] + (c2[2] - c1[2]) * j);
                blue = (int)(c1[3] + (c2[3] - c1[3]) * j);
                //palette[n + k] = new Color(red, green, blue);
                System.out.print("new Color(" +red + ", " + green + ", " + blue + "),");
            }
            n += c1[0];
        }
        System.out.print("}");*/
        
        switch (color_choice) {
            
            case 0:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(default_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(default_palette);
                }
                break;
            case 1:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(spectrum_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(spectrum_palette);
                }
                break;
            case 2:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative_palette);
                }
                break;
            case 3:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative2_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative2_palette);
                }
                break;
            case 4:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative3_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative3_palette);
                }
                break;
            case 5:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative4_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative4_palette);
                }
                break;
            case 6:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative5_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative5_palette);
                }
                break;   
            case 7:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative6_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative6_palette);
                }
                break;
            case 8:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative7_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative7_palette);
                }
                break;
            case 9:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(greenwhite_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(greenwhite_palette);
                }
                break;
            case 10:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(blue_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(blue_palette);
                }
                break;
            case 11:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(grayscale_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(grayscale_palette);
                }
                break;
            case 12:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(earthsky_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(earthsky_palette);
                }
                break;
            case 13:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(hotcold_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(hotcold_palette);
                }
                break;
            case 14:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(fire_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(fire_palette);
                }
                break;
                
        }

    }

    public Palette(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, int in_coloring_algorithm,  boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients,  double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, in_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        switch (color_choice) {
            
            case 0:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(default_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(default_palette);
                }
                break;
            case 1:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(spectrum_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(spectrum_palette);
                }
                break;
            case 2:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative_palette);
                }
                break;
            case 3:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative2_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative2_palette);
                }
                break;
            case 4:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative3_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative3_palette);
                }
                break;
            case 5:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative4_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative4_palette);
                }
                break;
            case 6:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative5_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative5_palette);
                }
                break;   
            case 7:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative6_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative6_palette);
                }
                break;
            case 8:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative7_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative7_palette);
                }
                break;
            case 9:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(greenwhite_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(greenwhite_palette);
                }
                break;
            case 10:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(blue_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(blue_palette);
                }
                break;
            case 11:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(grayscale_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(grayscale_palette);
                }
                break;
            case 12:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(earthsky_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(earthsky_palette);
                }
                break;
            case 13:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(hotcold_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(hotcold_palette);
                }
                break;
            case 14:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(fire_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(fire_palette);
                }
                break;
                
        }


    }
    
    public Palette(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, int in_coloring_algorithm,  boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
        
        switch (color_choice) {
            
            case 0:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(default_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(default_palette);
                }
                break;
            case 1:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(spectrum_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(spectrum_palette);
                }
                break;
            case 2:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative_palette);
                }
                break;
            case 3:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative2_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative2_palette);
                }
                break;
            case 4:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative3_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative3_palette);
                }
                break;
            case 5:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative4_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative4_palette);
                }
                break;
            case 6:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative5_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative5_palette);
                }
                break;   
            case 7:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative6_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative6_palette);
                }
                break;
            case 8:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative7_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative7_palette);
                }
                break;
            case 9:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(greenwhite_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(greenwhite_palette);
                }
                break;
            case 10:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(blue_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(blue_palette);
                }
                break;
            case 11:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(grayscale_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(grayscale_palette);
                }
                break;
            case 12:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(earthsky_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(earthsky_palette);
                }
                break;
            case 13:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(hotcold_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(hotcold_palette);
                }
                break;
            case 14:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(fire_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(fire_palette);
                }
                break;
                
        }


    }

    public Palette(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters, int out_coloring_algorithm, int in_coloring_algorithm,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double [] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, in_coloring_algorithm, filters,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        switch (color_choice) {
            
            case 0:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(default_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(default_palette);
                }
                break;
            case 1:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(spectrum_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(spectrum_palette);
                }
                break;
            case 2:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative_palette);
                }
                break;
            case 3:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative2_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative2_palette);
                }
                break;
            case 4:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative3_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative3_palette);
                }
                break;
            case 5:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative4_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative4_palette);
                }
                break;
            case 6:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative5_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative5_palette);
                }
                break;   
            case 7:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative6_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative6_palette);
                }
                break;
            case 8:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative7_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative7_palette);
                }
                break;
            case 9:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(greenwhite_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(greenwhite_palette);
                }
                break;
            case 10:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(blue_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(blue_palette);
                }
                break;
            case 11:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(grayscale_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(grayscale_palette);
                }
                break;
            case 12:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(earthsky_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(earthsky_palette);
                }
                break;
            case 13:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(hotcold_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(hotcold_palette);
                }
                break;
            case 14:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(fire_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(fire_palette);
                }
                break;
                
        }


    }

    public Palette(int color_choice, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, int out_coloring_algorithm,  BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);
      
        switch (color_choice) {
            
            case 0:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(default_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(default_palette);
                }
                break;
            case 1:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(spectrum_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(spectrum_palette);
                }
                break;
            case 2:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative_palette);
                }
                break;
            case 3:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative2_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative2_palette);
                }
                break;
            case 4:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative3_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative3_palette);
                }
                break;
            case 5:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative4_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative4_palette);
                }
                break;
            case 6:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative5_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative5_palette);
                }
                break;   
            case 7:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative6_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative6_palette);
                }
                break;
            case 8:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative7_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative7_palette);
                }
                break;
            case 9:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(greenwhite_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(greenwhite_palette);
                }
                break;
            case 10:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(blue_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(blue_palette);
                }
                break;
            case 11:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(grayscale_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(grayscale_palette);
                }
                break;
            case 12:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(earthsky_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(earthsky_palette);
                }
                break;
            case 13:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(hotcold_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(hotcold_palette);
                }
                break;
            case 14:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(fire_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(fire_palette);
                }
                break;
                
        }


    }

    public Palette(int color_choice, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, int out_coloring_algorithm,  boolean[] filters) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_cycling_location, filters);
        
        switch (color_choice) {
            
            case 0:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(default_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(default_palette);
                }
                break;
            case 1:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(spectrum_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(spectrum_palette);
                }
                break;
            case 2:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative_palette);
                }
                break;
            case 3:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative2_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative2_palette);
                }
                break;
            case 4:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative3_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative3_palette);
                }
                break;
            case 5:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative4_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative4_palette);
                }
                break;
            case 6:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative5_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative5_palette);
                }
                break;   
            case 7:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative6_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative6_palette);
                }
                break;
            case 8:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(alternative7_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(alternative7_palette);
                }
                break;
            case 9:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(greenwhite_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(greenwhite_palette);
                }
                break;
            case 10:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(blue_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(blue_palette);
                }
                break;
            case 11:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(grayscale_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(grayscale_palette);
                }
                break;
            case 12:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(earthsky_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(earthsky_palette);
                }
                break;
            case 13:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(hotcold_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(hotcold_palette);
                }
                break;
            case 14:
                if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                    palette_color = new PaletteColorSmooth(fire_palette);
                }
                else {
                    palette_color = new PaletteColorNormal(fire_palette);
                }
                break;
                
        }


    }

    
}
