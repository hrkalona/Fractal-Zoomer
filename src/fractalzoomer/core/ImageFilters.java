/*
 * Copyright (C) 2016 hrkalona
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
package fractalzoomer.core;

import fractalzoomer.utils.ColorSpaceConverter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferInt;
import java.awt.image.Kernel;

/**
 *
 * @author hrkalona
 */
public class ImageFilters {

    private static final float[] thick_edges = {-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -2.0f, -2.0f, -2.0f, -1.0f, -1.0f, -2.0f, 32.0f, -2.0f, -1.0f, -1.0f, -2.0f, -2.0f, -2.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f};
    private static final float[] thin_edges = {-1.0f, -1.0f, -1.0f, -1.0f, 8.0f, -1.0f, -1.0f, -1.0f, -1.0f};
    private static final float[] sharpness_high = {-0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, 3.4f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f};
    private static float[] sharpness_low = {0.0f, -0.2f, 0.0f, -0.2f, 1.8f, -0.2f, 0.0f, -0.2f, 0.0f};
    private static final float[] EMBOSS = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, -1.0f};
    private static final float blackBodyRGB[] = {1.0000F, 0.0401F, 0.0000F, 1.0000F, 0.0631F, 0.0000F, 1.0000F, 0.0860F, 0.0000F, 1.0000F, 0.1085F, 0.0000F, 1.0000F, 0.1303F, 0.0000F, 1.0000F, 0.1515F, 0.0000F, 1.0000F, 0.1718F, 0.0000F, 1.0000F, 0.1912F, 0.0000F, 1.0000F, 0.2097F, 0.0000F, 1.0000F, 0.2272F, 0.0000F, 1.0000F, 0.2484F, 0.0061F, 1.0000F, 0.2709F, 0.0153F, 1.0000F, 0.2930F, 0.0257F, 1.0000F, 0.3149F, 0.0373F, 1.0000F, 0.3364F, 0.0501F, 1.0000F, 0.3577F, 0.0640F, 1.0000F, 0.3786F, 0.0790F, 1.0000F, 0.3992F, 0.0950F, 1.0000F, 0.4195F, 0.1119F, 1.0000F, 0.4394F, 0.1297F, 1.0000F, 0.4589F, 0.1483F, 1.0000F, 0.4781F, 0.1677F, 1.0000F, 0.4970F, 0.1879F, 1.0000F, 0.5155F, 0.2087F, 1.0000F, 0.5336F, 0.2301F, 1.0000F, 0.5515F, 0.2520F, 1.0000F, 0.5689F, 0.2745F, 1.0000F, 0.5860F, 0.2974F, 1.0000F, 0.6028F, 0.3207F, 1.0000F, 0.6193F, 0.3444F, 1.0000F, 0.6354F, 0.3684F, 1.0000F, 0.6511F, 0.3927F, 1.0000F, 0.6666F, 0.4172F, 1.0000F, 0.6817F, 0.4419F, 1.0000F, 0.6966F, 0.4668F, 1.0000F, 0.7111F, 0.4919F, 1.0000F, 0.7253F, 0.5170F, 1.0000F, 0.7392F, 0.5422F, 1.0000F, 0.7528F, 0.5675F, 1.0000F, 0.7661F, 0.5928F, 1.0000F, 0.7792F, 0.6180F, 1.0000F, 0.7919F, 0.6433F, 1.0000F, 0.8044F, 0.6685F, 1.0000F, 0.8167F, 0.6937F, 1.0000F, 0.8286F, 0.7187F, 1.0000F, 0.8403F, 0.7437F, 1.0000F, 0.8518F, 0.7686F, 1.0000F, 0.8630F, 0.7933F, 1.0000F, 0.8740F, 0.8179F, 1.0000F, 0.8847F, 0.8424F, 1.0000F, 0.8952F, 0.8666F, 1.0000F, 0.9055F, 0.8907F, 1.0000F, 0.9156F, 0.9147F, 1.0000F, 0.9254F, 0.9384F, 1.0000F, 0.9351F, 0.9619F, 1.0000F, 0.9445F, 0.9853F, 0.9917F, 0.9458F, 1.0000F, 0.9696F, 0.9336F, 1.0000F, 0.9488F, 0.9219F, 1.0000F, 0.9290F, 0.9107F, 1.0000F, 0.9102F, 0.9000F, 1.0000F, 0.8923F, 0.8897F, 1.0000F, 0.8753F, 0.8799F, 1.0000F, 0.8591F, 0.8704F, 1.0000F, 0.8437F, 0.8614F, 1.0000F, 0.8289F, 0.8527F, 1.0000F, 0.8149F, 0.8443F, 1.0000F, 0.8014F, 0.8363F, 1.0000F, 0.7885F, 0.8285F, 1.0000F, 0.7762F, 0.8211F, 1.0000F, 0.7644F, 0.8139F, 1.0000F, 0.7531F, 0.8069F, 1.0000F, 0.7423F, 0.8002F, 1.0000F, 0.7319F, 0.7938F, 1.0000F, 0.7219F, 0.7875F, 1.0000F, 0.7123F, 0.7815F, 1.0000F, 0.7030F, 0.7757F, 1.0000F, 0.6941F, 0.7700F, 1.0000F, 0.6856F, 0.7645F, 1.0000F, 0.6773F, 0.7593F, 1.0000F, 0.6693F, 0.7541F, 1.0000F, 0.6617F, 0.7492F, 1.0000F, 0.6543F, 0.7444F, 1.0000F, 0.6471F, 0.7397F, 1.0000F, 0.6402F, 0.7352F, 1.0000F, 0.6335F, 0.7308F, 1.0000F, 0.6271F, 0.7265F, 1.0000F, 0.6208F, 0.7224F, 1.0000F, 0.6148F, 0.7183F, 1.0000F, 0.6089F, 0.7144F, 1.0000F, 0.6033F, 0.7106F, 1.0000F, 0.5978F, 0.7069F, 1.0000F, 0.5925F, 0.7033F, 1.0000F, 0.5873F, 0.6998F, 1.0000F, 0.5823F, 0.6964F, 1.0000F, 0.5774F, 0.6930F, 1.0000F, 0.5727F, 0.6898F, 1.0000F, 0.5681F, 0.6866F, 1.0000F, 0.5637F, 0.6836F, 1.0000F, 0.5593F, 0.6806F, 1.0000F, 0.5551F, 0.6776F, 1.0000F, 0.5510F, 0.6748F, 1.0000F, 0.5470F, 0.6720F, 1.0000F, 0.5432F, 0.6693F, 1.0000F, 0.5394F, 0.6666F, 1.0000F, 0.5357F, 0.6640F, 1.0000F, 0.5322F, 0.6615F, 1.0000F, 0.5287F, 0.6590F, 1.0000F, 0.5253F, 0.6566F, 1.0000F, 0.5220F, 0.6542F, 1.0000F, 0.5187F, 0.6519F, 1.0000F, 0.5156F, 0.6497F, 1.0000F, 0.5125F, 0.6474F, 1.0000F, 0.5095F, 0.6453F, 1.0000F, 0.5066F, 0.6432F, 1.0000F, 0.5037F, 0.6411F, 1.0000F, 0.5009F, 0.6391F, 1.0000F, 0.4982F, 0.6371F, 1.0000F, 0.4955F, 0.6351F, 1.0000F, 0.4929F, 0.6332F, 1.0000F, 0.4904F, 0.6314F, 1.0000F, 0.4879F, 0.6295F, 1.0000F, 0.4854F, 0.6277F, 1.0000F, 0.4831F, 0.6260F, 1.0000F, 0.4807F, 0.6243F, 1.0000F, 0.4785F, 0.6226F, 1.0000F, 0.4762F, 0.6209F, 1.0000F, 0.4740F, 0.6193F, 1.0000F, 0.4719F, 0.6177F, 1.0000F, 0.4698F, 0.6161F, 1.0000F, 0.4677F, 0.6146F, 1.0000F, 0.4657F, 0.6131F, 1.0000F, 0.4638F, 0.6116F, 1.0000F, 0.4618F, 0.6102F, 1.0000F, 0.4599F, 0.6087F, 1.0000F, 0.4581F, 0.6073F, 1.0000F, 0.4563F, 0.6060F, 1.0000F, 0.4545F, 0.6046F, 1.0000F, 0.4527F, 0.6033F, 1.0000F, 0.4510F, 0.6020F, 1.0000F, 0.4493F, 0.6007F, 1.0000F, 0.4477F, 0.5994F, 1.0000F, 0.4460F, 0.5982F, 1.0000F, 0.4445F, 0.5970F, 1.0000F, 0.4429F, 0.5958F, 1.0000F, 0.4413F, 0.5946F, 1.0000F, 0.4398F, 0.5935F, 1.0000F, 0.4384F, 0.5923F, 1.0000F, 0.4369F, 0.5912F, 1.0000F, 0.4355F, 0.5901F, 1.0000F, 0.4341F, 0.5890F, 1.0000F, 0.4327F, 0.5879F, 1.0000F, 0.4313F, 0.5869F, 1.0000F, 0.4300F, 0.5859F, 1.0000F, 0.4287F, 0.5848F, 1.0000F, 0.4274F, 0.5838F, 1.0000F, 0.4261F, 0.5829F, 1.0000F, 0.4249F, 0.5819F, 1.0000F, 0.4236F, 0.5809F, 1.0000F, 0.4224F, 0.5800F, 1.0000F, 0.4212F, 0.5791F, 1.0000F, 0.4201F, 0.5781F, 1.0000F, 0.4189F, 0.5772F, 1.0000F, 0.4178F, 0.5763F, 1.0000F, 0.4167F, 0.5755F, 1.0000F, 0.4156F, 0.5746F, 1.0000F, 0.4145F, 0.5738F, 1.0000F, 0.4134F, 0.5729F, 1.0000F, 0.4124F, 0.5721F, 1.0000F, 0.4113F, 0.5713F, 1.0000F, 0.4103F, 0.5705F, 1.0000F, 0.4093F, 0.5697F, 1.0000F, 0.4083F, 0.5689F, 1.0000F, 0.4074F, 0.5681F, 1.0000F, 0.4064F, 0.5674F, 1.0000F, 0.4055F, 0.5666F, 1.0000F, 0.4045F, 0.5659F, 1.0000F, 0.4036F, 0.5652F, 1.0000F, 0.4027F, 0.5644F, 1.0000F, 0.4018F, 0.5637F, 1.0000F, 0.4009F, 0.5630F, 1.0000F, 0.4001F, 0.5623F, 1.0000F, 0.3992F, 0.5616F, 1.0000F, 0.3984F, 0.5610F, 1.0000F, 0.3975F, 0.5603F, 1.0000F, 0.3967F, 0.5596F, 1.0000F, 0.3959F, 0.5590F, 1.0000F, 0.3951F, 0.5584F, 1.0000F, 0.3943F, 0.5577F, 1.0000F, 0.3935F, 0.5571F, 1.0000F, 0.3928F, 0.5565F, 1.0000F, 0.3920F, 0.5559F, 1.0000F, 0.3913F, 0.5553F, 1.0000F, 0.3905F, 0.5547F, 1.0000F, 0.3898F, 0.5541F, 1.0000F, 0.3891F, 0.5535F, 1.0000F, 0.3884F, 0.5529F, 1.0000F, 0.3877F, 0.5524F, 1.0000F, 0.3870F, 0.5518F, 1.0000F, 0.3863F, 0.5513F, 1.0000F, 0.3856F, 0.5507F, 1.0000F, 0.3850F, 0.5502F, 1.0000F, 0.3843F, 0.5496F, 1.0000F, 0.3836F, 0.5491F, 1.0000F, 0.3830F, 0.5486F, 1.0000F, 0.3824F, 0.5481F, 1.0000F, 0.3817F, 0.5476F, 1.0000F, 0.3811F, 0.5471F, 1.0000F, 0.3805F, 0.5466F, 1.0000F, 0.3799F, 0.5461F, 1.0000F, 0.3793F, 0.5456F, 1.0000F, 0.3787F, 0.5451F, 1.0000F, 0.3781F, 0.5446F, 1.0000F, 0.3776F, 0.5441F, 1.0000F, 0.3770F, 0.5437F, 1.0000F, 0.3764F, 0.5432F, 1.0000F, 0.3759F, 0.5428F, 1.0000F, 0.3753F, 0.5423F, 1.0000F, 0.3748F, 0.5419F, 1.0000F, 0.3742F, 0.5414F, 1.0000F, 0.3737F, 0.5410F, 1.0000F, 0.3732F, 0.5405F, 1.0000F, 0.3726F, 0.5401F, 1.0000F, 0.3721F, 0.5397F, 1.0000F, 0.3716F, 0.5393F, 1.0000F, 0.3711F, 0.5389F, 1.0000F, 0.3706F, 0.5384F, 1.0000F, 0.3701F, 0.5380F, 1.0000F, 0.3696F, 0.5376F, 1.0000F, 0.3692F, 0.5372F, 1.0000F, 0.3687F, 0.5368F, 1.0000F, 0.3682F, 0.5365F, 1.0000F, 0.3677F, 0.5361F, 1.0000F, 0.3673F, 0.5357F, 1.0000F, 0.3668F, 0.5353F, 1.0000F, 0.3664F, 0.5349F, 1.0000F, 0.3659F, 0.5346F, 1.0000F, 0.3655F, 0.5342F, 1.0000F, 0.3650F, 0.5338F, 1.0000F, 0.3646F, 0.5335F, 1.0000F, 0.3642F, 0.5331F, 1.0000F, 0.3637F, 0.5328F, 1.0000F, 0.3633F, 0.5324F, 1.0000F, 0.3629F, 0.5321F, 1.0000F, 0.3625F, 0.5317F, 1.0000F, 0.3621F, 0.5314F, 1.0000F, 0.3617F, 0.5310F, 1.0000F, 0.3613F, 0.5307F, 1.0000F, 0.3609F, 0.5304F, 1.0000F, 0.3605F, 0.5300F, 1.0000F, 0.3601F, 0.5297F, 1.0000F, 0.3597F, 0.5294F, 1.0000F, 0.3593F, 0.5291F, 1.0000F, 0.3589F, 0.5288F, 1.0000F, 0.3586F, 0.5284F, 1.0000F, 0.3582F, 0.5281F, 1.0000F, 0.3578F, 0.5278F, 1.0000F, 0.3575F, 0.5275F, 1.0000F, 0.3571F, 0.5272F, 1.0000F, 0.3567F, 0.5269F, 1.0000F, 0.3564F, 0.5266F, 1.0000F, 0.3560F, 0.5263F, 1.0000F, 0.3557F, 0.5260F, 1.0000F, 0.3553F, 0.5257F, 1.0000F, 0.3550F, 0.5255F, 1.0000F, 0.3546F, 0.5252F, 1.0000F, 0.3543F, 0.5249F, 1.0000F, 0.3540F, 0.5246F, 1.0000F, 0.3536F, 0.5243F, 1.0000F, 0.3533F, 0.5241F, 1.0000F, 0.3530F, 0.5238F, 1.0000F, 0.3527F, 0.5235F, 1.0000F, 0.3524F, 0.5232F, 1.0000F, 0.3520F, 0.5230F, 1.0000F, 0.3517F, 0.5227F, 1.0000F, 0.3514F, 0.5225F, 1.0000F, 0.3511F, 0.5222F, 1.0000F, 0.3508F, 0.5219F, 1.0000F, 0.3505F, 0.5217F, 1.0000F, 0.3502F, 0.5214F, 1.0000F, 0.3499F, 0.5212F, 1.0000F, 0.3496F, 0.5209F, 1.0000F, 0.3493F, 0.5207F, 1.0000F, 0.3490F, 0.5204F, 1.0000F, 0.3487F, 0.5202F, 1.0000F, 0.3485F, 0.5200F, 1.0000F, 0.3482F, 0.5197F, 1.0000F, 0.3479F, 0.5195F, 1.0000F, 0.3476F, 0.5192F, 1.0000F, 0.3473F, 0.5190F, 1.0000F, 0.3471F, 0.5188F, 1.0000F, 0.3468F, 0.5186F, 1.0000F, 0.3465F, 0.5183F, 1.0000F, 0.3463F, 0.5181F, 1.0000F, 0.3460F, 0.5179F, 1.0000F, 0.3457F, 0.5177F, 1.0000F, 0.3455F, 0.5174F, 1.0000F, 0.3452F, 0.5172F, 1.0000F, 0.3450F, 0.5170F, 1.0000F, 0.3447F, 0.5168F, 1.0000F, 0.3444F, 0.5166F, 1.0000F, 0.3442F, 0.5164F, 1.0000F, 0.3439F, 0.5161F, 1.0000F, 0.3437F, 0.5159F, 1.0000F, 0.3435F, 0.5157F, 1.0000F, 0.3432F, 0.5155F, 1.0000F, 0.3430F, 0.5153F, 1.0000F, 0.3427F, 0.5151F, 1.0000F, 0.3425F, 0.5149F, 1.0000F, 0.3423F, 0.5147F, 1.0000F, 0.3420F, 0.5145F, 1.0000F, 0.3418F, 0.5143F, 1.0000F, 0.3416F, 0.5141F, 1.0000F, 0.3413F, 0.5139F, 1.0000F, 0.3411F, 0.5137F, 1.0000F, 0.3409F, 0.5135F, 1.0000F, 0.3407F, 0.5133F, 1.0000F, 0.3404F, 0.5132F, 1.0000F, 0.3402F, 0.5130F, 1.0000F, 0.3400F, 0.5128F, 1.0000F, 0.3398F, 0.5126F, 1.0000F, 0.3396F, 0.5124F, 1.0000F, 0.3393F, 0.5122F, 1.0000F, 0.3391F, 0.5120F, 1.0000F, 0.3389F, 0.5119F, 1.0000F, 0.3387F, 0.5117F, 1.0000F, 0.3385F, 0.5115F, 1.0000F, 0.3383F, 0.5113F, 1.0000F, 0.3381F, 0.5112F, 1.0000F, 0.3379F, 0.5110F, 1.0000F, 0.3377F, 0.5108F, 1.0000F, 0.3375F, 0.5106F, 1.0000F, 0.3373F, 0.5105F, 1.0000F, 0.3371F, 0.5103F, 1.0000F, 0.3369F, 0.5101F, 1.0000F, 0.3367F, 0.5100F, 1.0000F, 0.3365F, 0.5098F, 1.0000F, 0.3363F, 0.5096F, 1.0000F, 0.3361F, 0.5095F, 1.0000F, 0.3359F, 0.5093F, 1.0000F, 0.3357F, 0.5091F, 1.0000F, 0.3356F, 0.5090F, 1.0000F, 0.3354F, 0.5088F, 1.0000F, 0.3352F, 0.5087F, 1.0000F, 0.3350F, 0.5085F, 1.0000F, 0.3348F, 0.5084F, 1.0000F, 0.3346F, 0.5082F, 1.0000F, 0.3345F, 0.5080F, 1.0000F, 0.3343F, 0.5079F, 1.0000F, 0.3341F, 0.5077F, 1.0000F, 0.3339F, 0.5076F, 1.0000F, 0.3338F, 0.5074F, 1.0000F, 0.3336F, 0.5073F, 1.0000F, 0.3334F, 0.5071F, 1.0000F, 0.3332F, 0.5070F, 1.0000F, 0.3331F, 0.5068F, 1.0000F, 0.3329F, 0.5067F, 1.0000F, 0.3327F, 0.5066F, 1.0000F, 0.3326F, 0.5064F, 1.0000F, 0.3324F, 0.5063F, 1.0000F, 0.3322F, 0.5061F, 1.0000F, 0.3321F, 0.5060F, 1.0000F, 0.3319F, 0.5058F, 1.0000F, 0.3317F, 0.5057F, 1.0000F, 0.3316F, 0.5056F, 1.0000F, 0.3314F, 0.5054F, 1.0000F, 0.3313F, 0.5053F, 1.0000F, 0.3311F, 0.5052F, 1.0000F, 0.3309F, 0.5050F, 1.0000F, 0.3308F, 0.5049F, 1.0000F, 0.3306F, 0.5048F, 1.0000F, 0.3305F, 0.5046F, 1.0000F, 0.3303F, 0.5045F, 1.0000F, 0.3302F, 0.5044F, 1.0000F, 0.3300F, 0.5042F, 1.0000F, 0.3299F, 0.5041F, 1.0000F, 0.3297F, 0.5040F, 1.0000F, 0.3296F, 0.5038F, 1.0000F, 0.3294F, 0.5037F, 1.0000F, 0.3293F, 0.5036F, 1.0000F, 0.3291F, 0.5035F, 1.0000F, 0.3290F, 0.5033F, 1.0000F, 0.3288F, 0.5032F, 1.0000F, 0.3287F, 0.5031F, 1.0000F, 0.3286F, 0.5030F, 1.0000F, 0.3284F, 0.5028F, 1.0000F, 0.3283F, 0.5027F, 1.0000F, 0.3281F, 0.5026F, 1.0000F, 0.3280F, 0.5025F, 1.0000F, 0.3279F, 0.5024F, 1.0000F, 0.3277F, 0.5022F, 1.0000F};

    /**
     * 2x2 magic square.
     */
    private final static int[] ditherMagic2x2Matrix = {
        0, 2,
        3, 1
    };

    /**
     * 4x4 magic square.
     */
    private final static int[] ditherMagic4x4Matrix = {
        0, 14, 3, 13,
        11, 5, 8, 6,
        12, 2, 15, 1,
        7, 9, 4, 10
    };

    /**
     * 4x4 ordered dither.
     */
    private final static int[] ditherOrdered4x4Matrix = {
        0, 8, 2, 10,
        12, 4, 14, 6,
        3, 11, 1, 9,
        15, 7, 13, 5
    };

    /**
     * 4x4 lines.
     */
    private final static int[] ditherLines4x4Matrix = {
        0, 1, 2, 3,
        4, 5, 6, 7,
        8, 9, 10, 11,
        12, 13, 14, 15
    };

    /**
     * 6x6 90 degree halftone.
     */
    private final static int[] dither90Halftone6x6Matrix = {
        29, 18, 12, 19, 30, 34,
        17, 7, 4, 8, 20, 28,
        11, 3, 0, 1, 9, 27,
        16, 6, 2, 5, 13, 26,
        25, 15, 10, 14, 21, 31,
        33, 25, 24, 23, 33, 36
    };

    /*
     * The following dithering matrices are taken from "Digital Halftoning" 
     * by Robert Ulichney, MIT Press, ISBN 0-262-21009-6.
     */
    /**
     * Order-6 ordered dither.
     */
    private final static int[] ditherOrdered6x6Matrix = {
        1, 59, 15, 55, 2, 56, 12, 52,
        33, 17, 47, 31, 34, 18, 44, 28,
        9, 49, 5, 63, 10, 50, 6, 60,
        41, 25, 37, 21, 42, 26, 38, 22,
        3, 57, 13, 53, 0, 58, 14, 54,
        35, 19, 45, 29, 32, 16, 46, 30,
        11, 51, 7, 61, 8, 48, 4, 62,
        43, 27, 39, 23, 40, 24, 36, 20
    };

    /**
     * Order-8 ordered dither.
     */
    private final static int[] ditherOrdered8x8Matrix = {
        1, 235, 59, 219, 15, 231, 55, 215, 2, 232, 56, 216, 12, 228, 52, 212,
        129, 65, 187, 123, 143, 79, 183, 119, 130, 66, 184, 120, 140, 76, 180, 116,
        33, 193, 17, 251, 47, 207, 31, 247, 34, 194, 18, 248, 44, 204, 28, 244,
        161, 97, 145, 81, 175, 111, 159, 95, 162, 98, 146, 82, 172, 108, 156, 92,
        9, 225, 49, 209, 5, 239, 63, 223, 10, 226, 50, 210, 6, 236, 60, 220,
        137, 73, 177, 113, 133, 69, 191, 127, 138, 74, 178, 114, 134, 70, 188, 124,
        41, 201, 25, 241, 37, 197, 21, 255, 42, 202, 26, 242, 38, 198, 22, 252,
        169, 105, 153, 89, 165, 101, 149, 85, 170, 106, 154, 90, 166, 102, 150, 86,
        3, 233, 57, 217, 13, 229, 53, 213, 0, 234, 58, 218, 14, 230, 54, 214,
        131, 67, 185, 121, 141, 77, 181, 117, 128, 64, 186, 122, 142, 78, 182, 118,
        35, 195, 19, 249, 45, 205, 29, 245, 32, 192, 16, 250, 46, 206, 30, 246,
        163, 99, 147, 83, 173, 109, 157, 93, 160, 96, 144, 80, 174, 110, 158, 94,
        11, 227, 51, 211, 7, 237, 61, 221, 8, 224, 48, 208, 4, 238, 62, 222,
        139, 75, 179, 115, 135, 71, 189, 125, 136, 72, 176, 112, 132, 68, 190, 126,
        43, 203, 27, 243, 39, 199, 23, 253, 40, 200, 24, 240, 36, 196, 20, 254,
        171, 107, 155, 91, 167, 103, 151, 87, 168, 104, 152, 88, 164, 100, 148, 84};

    /**
     * Order-3 clustered dither.
     */
    private final static int[] ditherCluster3Matrix = {
        9, 11, 10, 8, 6, 7,
        12, 17, 16, 5, 0, 1,
        13, 14, 15, 4, 3, 2,
        8, 6, 7, 9, 11, 10,
        5, 0, 1, 12, 17, 16,
        4, 3, 2, 13, 14, 15};

    /**
     * Order-4 clustered dither.
     */
    private final static int[] ditherCluster4Matrix = {
        18, 20, 19, 16, 13, 11, 12, 15,
        27, 28, 29, 22, 4, 3, 2, 9,
        26, 31, 30, 21, 5, 0, 1, 10,
        23, 25, 24, 17, 8, 6, 7, 14,
        13, 11, 12, 15, 18, 20, 19, 16,
        4, 3, 2, 9, 27, 28, 29, 22,
        5, 0, 1, 10, 26, 31, 30, 21,
        8, 6, 7, 14, 23, 25, 24, 17};

    /**
     * Order-8 clustered dither.
     */
    private final static int[] ditherCluster8Matrix = {
        64, 69, 77, 87, 86, 76, 68, 67, 63, 58, 50, 40, 41, 51, 59, 60,
        70, 94, 100, 109, 108, 99, 93, 75, 57, 33, 27, 18, 19, 28, 34, 52,
        78, 101, 114, 116, 115, 112, 98, 83, 49, 26, 13, 11, 12, 15, 29, 44,
        88, 110, 123, 124, 125, 118, 107, 85, 39, 17, 4, 3, 2, 9, 20, 42,
        89, 111, 122, 127, 126, 117, 106, 84, 38, 16, 5, 0, 1, 10, 21, 43,
        79, 102, 119, 121, 120, 113, 97, 82, 48, 25, 8, 6, 7, 14, 30, 45,
        71, 95, 103, 104, 105, 96, 92, 74, 56, 32, 24, 23, 22, 31, 35, 53,
        65, 72, 80, 90, 91, 81, 73, 66, 62, 55, 47, 37, 36, 46, 54, 61,
        63, 58, 50, 40, 41, 51, 59, 60, 64, 69, 77, 87, 86, 76, 68, 67,
        57, 33, 27, 18, 19, 28, 34, 52, 70, 94, 100, 109, 108, 99, 93, 75,
        49, 26, 13, 11, 12, 15, 29, 44, 78, 101, 114, 116, 115, 112, 98, 83,
        39, 17, 4, 3, 2, 9, 20, 42, 88, 110, 123, 124, 125, 118, 107, 85,
        38, 16, 5, 0, 1, 10, 21, 43, 89, 111, 122, 127, 126, 117, 106, 84,
        48, 25, 8, 6, 7, 14, 30, 45, 79, 102, 119, 121, 120, 113, 97, 82,
        56, 32, 24, 23, 22, 31, 35, 53, 71, 95, 103, 104, 105, 96, 92, 74,
        62, 55, 47, 37, 36, 46, 54, 61, 65, 72, 80, 90, 91, 81, 73, 66};

    public static void filterEmboss(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        if(filter_value == 1) {
            int kernelWidth = (int)Math.sqrt((double)EMBOSS.length);
            int kernelHeight = kernelWidth;
            int xOffset = (kernelWidth - 1) / 2;
            int yOffset = xOffset;

            BufferedImage newSource = new BufferedImage(image_size + kernelWidth - 1, image_size + kernelHeight - 1, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = newSource.createGraphics();
            graphics.drawImage(image, xOffset, yOffset, null);

            Kernel kernel = new Kernel(kernelWidth, kernelHeight, EMBOSS);
            ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            cop.filter(newSource, image);

            graphics.dispose();
            graphics = null;
            kernel = null;
            cop = null;
            newSource = null;
        }
        else {
            int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

            for(int i = image_size - 1; i >= 0; i--) {
                for(int j = image_size - 1, loc = i * image_size + j; j >= 0; j--, loc--) {
                    int current = raster[loc];

                    int upperLeft = 0;
                    if(i > 0 && j > 0) {
                        upperLeft = raster[loc - image_size - 1];
                    }

                    int rDiff = ((current >> 16) & 0xff) - ((upperLeft >> 16) & 0xff);
                    int gDiff = ((current >> 8) & 0xff) - ((upperLeft >> 8) & 0xff);
                    int bDiff = (current & 0xff) - (upperLeft & 0xff);

                    int diff = rDiff;
                    if(Math.abs(gDiff) > Math.abs(diff)) {
                        diff = gDiff;
                    }
                    if(Math.abs(bDiff) > Math.abs(diff)) {
                        diff = bDiff;
                    }

                    int grayLevel = Math.max(Math.min(128 + diff, 255), 0);
                    raster[loc] = 0xff000000 | ((grayLevel << 16) + (grayLevel << 8) + grayLevel);
                }
            }
        }

    }

    public static void filterEdgeDetection(BufferedImage image, int filter_value) {

        /*float[] EDGES = {1.0f,   1.0f,  1.0f,
         1.0f, -8.0f,  1.0f,
         1.0f,   1.0f,  1.0f};*/

        /*float[] EDGES = {-7.0f,   -7.0f,  -7.0f,
         -7.0f, 56.0f,  -7.0f,
         -7.0f,   -7.0f,  -7.0f};*/

        /*float[] EDGES = {-1.0f, -1.0f, -2.0f, -1.0f, -1.0f,
         -1.0f, -2.0f, -4.0f, -2.0f, -1.0f,
         -2.0f, -4.0f, 44.0f, -4.0f, -2.0f,
         -1.0f, -2.0f, -4.0f, -2.0f, -1.0f,
         -1.0f, -1.0f, -2.0f, -1.0f, -1.0f};*/
        float[] EDGES = null;

        if(filter_value == 1) {
            EDGES = thick_edges;
        }
        else {
            EDGES = thin_edges;
        }

        int kernelWidth = (int)Math.sqrt((double)EDGES.length);

        int image_size = image.getHeight();

        Kernel kernel = new Kernel(kernelWidth, kernelWidth, EDGES);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        BufferedImage newSource = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
        BufferedImage newSource2 = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);

        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)newSource.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        cop.filter(newSource, newSource2);

        int black = Color.BLACK.getRGB();

        int[] raster = ((DataBufferInt)newSource2.getRaster().getDataBuffer()).getData();
        int[] rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            if((0xff000000 | raster[p]) == black) {
                rgbs[p] = black;
            }
        }

        kernel = null;
        cop = null;
        newSource = null;
        newSource2 = null;

    }

    public static void filterSharpness(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        float[] SHARPNESS = null;

        if(filter_value == 0) {
            SHARPNESS = sharpness_low;
        }
        else {
            SHARPNESS = sharpness_high;
        }

        int kernelWidth = (int)Math.sqrt((double)SHARPNESS.length);
        int kernelHeight = kernelWidth;
        int xOffset = (kernelWidth - 1) / 2;
        int yOffset = xOffset;

        BufferedImage newSource = new BufferedImage(image_size + kernelWidth - 1, image_size + kernelHeight - 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newSource.createGraphics();
        graphics.drawImage(image, xOffset, yOffset, null);

        Kernel kernel = new Kernel(kernelWidth, kernelHeight, SHARPNESS);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(newSource, image);

        graphics.dispose();
        graphics = null;
        kernel = null;
        cop = null;
        newSource = null;
    }

    public static void filterBlurring(BufferedImage image, int filter_value) { //OLD antialiasing method (blurring)
        
        int radius = ((int)(filter_value / 1000.0));
        double weight = ((int)(filter_value % 1000.0)) / 100.0 * 20;

        /*     float b = 0.05f;
         float a = 1.0f - (8.0f * b);
        
        
         float[] AA = {b, b, b,    // low-pass filter
         b, a, b,
         b, b, b};
        
        
         /* float c = 0.00390625f;
         float b = 0.046875f;
         float a = 1.0f - (16.0f * c + 8.0f * b);
        
         float[] AA = {c, c, c, c, c,    // low-pass filter
         c, b, b, b, c,
         c, b, a, b, c,
         c, b, b, b, c,
         c, c, c, c, c};*/
        /*  float d = 1.0f / 3096.0f;
         float c = 12.0f * d;
         float b = 12.0f * c;
         float a = 1.0f - (24.0f * d + 16.0f * c + 8.0f * b);
        
         float[] AA = {d, d, d, d, d, d, d,    // low-pass filter
         d, c, c, c, c, c, d,
         d, c, b, b, b, c, d,
         d, c, b, a, b, c, d,
         d, c, b, b, b, c, d,
         d, c, c, c, c, c, d,
         d, d, d, d, d, d, d};*/
        float[] blur = null;
   
        /*if(blurring == 1) {
         float[] MOTION_BLUR = {0.2f,  0.0f,  0.0f, 0.0f,  0.0f,
         0.0f,   0.2f,  0.0f, 0.0f,  0.0f,
         0.0f,   0.0f,  0.2f, 0.0f,  0.0f,
         0.0f,   0.0f,  0.0f, 0.2f,  0.0f,
         0.0f,   0.0f,  0.0f, 0.0f,  0.2f};
        
         blur = MOTION_BLUR;
         }*/
        //else {
        
        
        if(radius == 0) {       
            float e = 1.0f / 37184.0f;
            float d = 12.0f * e;
            float c = 12.0f * d;
            float b = 12.0f * c;
            float a = 1.0f - (32.0f * e + 24.0f * d + 16.0f * c + 8.0f * b);

            float[] NORMAL_BLUR = {e, e, e, e, e, e, e, e, e, // low-pass filter
                e, d, d, d, d, d, d, d, e,
                e, d, c, c, c, c, c, d, e,
                e, d, c, b, b, b, c, d, e,
                e, d, c, b, a, b, c, d, e,
                e, d, c, b, b, b, c, d, e,
                e, d, c, c, c, c, c, d, e,
                e, d, d, d, d, d, d, d, e,
                e, e, e, e, e, e, e, e, e};
        
            blur = NORMAL_BLUR;
        }
        else {
            blur = createGaussianKernel((radius - 1) * 2 + 3, weight);
        }

        // }


        /*float h = 1.0f / 64260344.0f;
         float g = 12.0f * h;
         float f = 12.0f * g;
         float e = 12.0f * f;
         float d = 12.0f * e;
         float c = 12.0f * d;
         float b = 12.0f * c;
         float a = 1.0f - (56.0f * h + 48.0f * g + 40.0f * f + 32.0f * e + 24.0f * d + 16.0f * c + 8.0f * b);
        
         float[] AA  = {h, h, h, h, h, h, h, h, h, h, h, h, h, h, h,    // low-pass filter
         h, g, g, g, g, g, g, g, g, g, g, g, g, g, h,
         h, g, f, f, f, f, f, f, f, f, f, f, f, g, h,
         h, g, f, e, e, e, e, e, e, e, e, e, f, g, h,
         h, g, f, e, d, d, d, d, d, d, d, e, f, g, h,
         h, g, f, e, d, c, c, c, c, c, d, e, f, g, h,
         h, g, f, e, d, c, b, b, b, c, d, e, f, g, h,
         h, g, f, e, d, c, b, a, b, c, d, e, f, g, h,
         h, g, f, e, d, c, b, b, b, c, d, e, f, g, h,
         h, g, f, e, d, c, c, c, c, c, d, e, f, g, h,
         h, g, f, e, d, d, d, d, d, d, d, e, f, g, h,
         h, g, f, e, e, e, e, e, e, e, e, e, f, g, h,
         h, g, f, f, f, f, f, f, f, f, f, f, f, g, h,
         h, g, g, g, g, g, g, g, g, g, g, g, g, g, h,
         h, h, h, h, h, h, h, h, h, h, h, h, h, h, h};*/
        //resize the picture to cover the image edges
        int kernelWidth = (int)Math.sqrt((double)blur.length);
        int kernelHeight = kernelWidth;
        int xOffset = (kernelWidth - 1) / 2;
        int yOffset = xOffset;

        int image_size = image.getHeight();

        BufferedImage newSource = new BufferedImage(image_size + kernelWidth - 1, image_size + kernelHeight - 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newSource.createGraphics();
        graphics.drawImage(image, xOffset, yOffset, null);

        Kernel kernel = new Kernel(kernelWidth, kernelHeight, blur);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(newSource, image);

        graphics.dispose();
        graphics = null;
        blur = null;
        kernel = null;
        cop = null;
        newSource = null;
    }

    public static void filterInvertColors(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        int r, g, b;

        for(int p = 0; p < condition; p++) {

            if(filter_value == 0) {
                raster[p] = ~(raster[p] & 0x00ffffff);
            }
            else if(filter_value == 1){ // Brightness
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                raster[p] = Color.HSBtoRGB(res[0], res[1], 1.0f - res[2]);
            }
            else if(filter_value == 2){ // Hue
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                raster[p] = Color.HSBtoRGB(1.0f - res[0], res[1], res[2]);
            }
            else if(filter_value == 3){ // Saturation
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                raster[p] = Color.HSBtoRGB(res[0], 1.0f - res[1], res[2]);
            }

        }

    }

    public static void filterMaskColors(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int mask = 0;

        if(filter_value == 0) {
            mask = 0xff00ffff;  //RED
        }
        else if(filter_value == 1) {
            mask = 0xffff00ff;  //GREEN
        }
        else {
            mask = 0xffffff00;  //BLUE
        }

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            raster[p] = raster[p] & mask;
        }

    }

    public static void filterFadeOut(BufferedImage image) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        int r, g, b;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            r = (r + 255) >> 1;
            g = (g + 255) >> 1;
            b = (b + 255) >> 1;

            raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }

    }

    public static void filterColorChannelSwapping(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        int r, g, b;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            if(filter_value == 0) {
                raster[p] = 0xff000000 | (r << 16) | (b << 8) | g;
            }
            else if(filter_value == 1) {
                raster[p] = 0xff000000 | (g << 16) | (r << 8) | b;
            }
            else if(filter_value == 2) {
                raster[p] = 0xff000000 | (g << 16) | (b << 8) | r;
            }
            else if(filter_value == 3) {
                raster[p] = 0xff000000 | (b << 16) | (g << 8) | r;
            }
            else {
                raster[p] = 0xff000000 | (b << 16) | (r << 8) | g;
            }
        }

    }

    public static void filterContrastBrightness(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        double contrast = (((int)(filter_value / 1000.0)) / 100.0) * 2.0;
        double brightness = (((int)(filter_value % 1000.0)) / 100.0) * 2.0;

        int[] table = new int[256];
        double f;

        for(int i = 0; i < 256; i++) {
            f = i / 255.0;
            f = f * brightness;
            f = (f - 0.5) * contrast + 0.5;
            table[i] = ColorSpaceConverter.clamp((int)(255 * f));
        }

        int r, g, b;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            raster[p] = 0xff000000 | (table[r] << 16) | (table[g] << 8) | table[b];
        }

    }

    public static void filterColorTemperature(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int t = 3 * (int)((((double)filter_value) - 1000.0) / 100.0);
        double rFactor = blackBodyRGB[t];
        double gFactor = blackBodyRGB[t + 1];
        double bFactor = blackBodyRGB[t + 2];
        int r, g, b;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            r = (int)(r * rFactor + 0.5);
            g = (int)(g * gFactor + 0.5);
            b = (int)(b * bFactor + 0.5);

            raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }

    }

    public static void filterGrayscale(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int r, g, b, rgb;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            if(filter_value == 0) {
                rgb = (r * 612 + g * 1202 + b * 233) >> 11;	// NTSC luma
                //rgb = (int)(r * 0.299F + g * 0.587F + b * 0.114F);	// NTSC luma
            }
            else if(filter_value == 1) {
                rgb = (r * 435 + g * 1464 + b * 147) >> 11;	// HDTV luma
                //rgb = (int)(r * 0.2126F + g * 0.7152F + b * 0.0722F);	// HDTV luma
            }
            else if(filter_value == 2) {
                rgb = (int)((r + g + b) / 3.0 + 0.5);	// simple average
            }
            else {
                rgb = (int)((Math.max(Math.max(r, g), b) + Math.min(Math.min(r, g), b)) / 2.0 + 0.5);
            }

            raster[p] = 0xff000000 | (rgb << 16) | (rgb << 8) | rgb;
        }

    }

    public static void filterHistogramEqualization(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        /*int r, g, b;
        
         int cdf[] = new int[100001];
        
         for(int p = 0; p < condition; p++) {
         r = (raster[p] >> 16) & 0xff;
         g = (raster[p] >> 8) & 0xff;
         b = raster[p] & 0xff;
        
         float res[] = new float[3];
        
         Color.RGBtoHSB(r, g, b, res);
        
         cdf[(int)(res[2] * 100000 + 0.5)]++;
         }
        
         int min = 0;
         int j = 0;
         while(min == 0) {
         min = cdf[j++];
         }
         int d = condition - min;
        
         for(int i = 1; i < cdf.length; i++) {
         cdf[i] += cdf[i - 1];
         }
        
         for(int p = 0; p < condition; p++) {
         r = (raster[p] >> 16) & 0xff;
         g = (raster[p] >> 8) & 0xff;
         b = raster[p] & 0xff;
        
         float res[] = new float[3];
        
         Color.RGBtoHSB(r, g, b, res);
        
         double temp = ((double)(cdf[(int)(res[2] * 100000 + 0.5)] - min)) / d;
        
         raster[p] = Color.HSBtoRGB(res[0], res[1], (float)(temp < 0 ? 0 : temp));
        
        
         }*/
        if(filter_value == 1) { //gimp levels

            int hist[][] = new int[3][256];

            int i, c;
            double mult, count, percentage, next_percentage;
            int low = 0, high = 0;

            // Fill the histogram by counting the number of pixels with each value
            for(int p = 0; p < condition; p++) {
                hist[0][(raster[p] >> 16) & 0xff]++;
                hist[1][(raster[p] >> 8) & 0xff]++;
                hist[2][raster[p] & 0xff]++;
            }

            count = image_size * image_size;
            // Fore each channel
            for(c = 0; c < hist.length; c++) {
                // Determine the low input value
                next_percentage = hist[c][0] / count;
                for(i = 0; i < 255; i++) {
                    percentage = next_percentage;
                    next_percentage += hist[c][i + 1] / count;
                    if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                        //low = i;//i+1; This is a deviation from the way The GIMP does it
                        low = i + 1;
                        // that prevents any change in the image if it's
                        // already optimal
                        break;
                    }
                }
                // Determine the high input value
                next_percentage = hist[c][255] / count;
                for(i = 255; i > 0; i--) {
                    percentage = next_percentage;
                    next_percentage += hist[c][i - 1] / count;
                    if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                        //high = i;//i-1; This is a deviation from the way The GIMP does it
                        high = i - 1;
                        // that prevents any change in the image if it's
                        // already optimal
                        break;
                    }
                }

                // Turn the histogram into a look up table to stretch the values
                mult = 255.0 / (high - low);

                for(i = 0; i < low; i++) {
                    hist[c][i] = 0;
                }

                for(i = 255; i > high; i--) {
                    hist[c][i] = 255;
                }

                double base = 0.0;

                for(i = low; i <= high; i++) {
                    hist[c][i] = (int)(base + 0.5);
                    base += mult;
                }
            }

            // Now apply the changes (stretch the values)
            int r, g, b;

            for(int p = 0; p < condition; p++) {
                r = hist[0][(raster[p] >> 16) & 0xff];
                g = hist[1][(raster[p] >> 8) & 0xff];
                b = hist[2][raster[p] & 0xff];

                raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
            }
        }
        else if(filter_value == 0) { //brightness
            int hist[] = new int[1025];

            int i;
            double mult, count, percentage, next_percentage;
            int low = 0, high = 0;

            int hist_len = hist.length - 1;

            int r, g, b;

            // Fill the histogram by counting the number of pixels with each value
            for(int p = 0; p < condition; p++) {
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                hist[(int)(res[2] * hist_len + 0.5)]++;
            }

            count = image_size * image_size;

            // Determine the low input value
            next_percentage = hist[0] / count;
            for(i = 0; i < hist_len; i++) {
                percentage = next_percentage;
                next_percentage += hist[i + 1] / count;
                if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                    //low = i;//i+1; This is a deviation from the way The GIMP does it
                    low = i + 1;
                    // that prevents any change in the image if it's
                    // already optimal
                    break;
                }
            }
            // Determine the high input value
            next_percentage = hist[hist_len] / count;
            for(i = hist_len; i > 0; i--) {
                percentage = next_percentage;
                next_percentage += hist[i - 1] / count;
                if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                    //high = i;//i-1; This is a deviation from the way The GIMP does it
                    high = i - 1;
                    // that prevents any change in the image if it's
                    // already optimal
                    break;
                }
            }

            // Turn the histogram into a look up table to stretch the values
            mult = ((double)hist_len) / (high - low);

            for(i = 0; i < low; i++) {
                hist[i] = 0;
            }

            for(i = hist_len; i > high; i--) {
                hist[i] = hist_len;
            }

            double base = 0.0;

            for(i = low; i <= high; i++) {
                hist[i] = (int)(base + 0.5);
                base += mult;
            }

            // Now apply the changes (stretch the values)
            for(int p = 0; p < condition; p++) {
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                double temp = hist[(int)(res[2] * hist_len + 0.5)] / ((double)hist_len);

                raster[p] = Color.HSBtoRGB(res[0], res[1], (float)temp);
            }

        }
        else if(filter_value == 2 || filter_value == 3 || filter_value == 4) { //red, green, blue
            int hist[] = new int[256];

            int i;
            double mult, count, percentage, next_percentage;
            int low = 0, high = 0;

            // Fill the histogram by counting the number of pixels with each value
            for(int p = 0; p < condition; p++) {
                if(filter_value == 2) {
                    hist[(raster[p] >> 16) & 0xff]++;
                }
                else if(filter_value == 3) {
                    hist[(raster[p] >> 8) & 0xff]++;
                }
                else {
                    hist[raster[p] & 0xff]++;
                }
            }

            count = image_size * image_size;
            // Determine the low input value
            next_percentage = hist[0] / count;
            for(i = 0; i < 255; i++) {
                percentage = next_percentage;
                next_percentage += hist[i + 1] / count;
                if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                    //low = i;//i+1; This is a deviation from the way The GIMP does it
                    low = i + 1;
                    // that prevents any change in the image if it's
                    // already optimal
                    break;
                }
            }
            // Determine the high input value
            next_percentage = hist[255] / count;
            for(i = 255; i > 0; i--) {
                percentage = next_percentage;
                next_percentage += hist[i - 1] / count;
                if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                    //high = i;//i-1; This is a deviation from the way The GIMP does it
                    high = i - 1;
                    // that prevents any change in the image if it's
                    // already optimal
                    break;
                }
            }

            // Turn the histogram into a look up table to stretch the values
            mult = 255.0 / (high - low);

            for(i = 0; i < low; i++) {
                hist[i] = 0;
            }

            for(i = 255; i > high; i--) {
                hist[i] = 255;
            }

            double base = 0.0;

            for(i = low; i <= high; i++) {
                hist[i] = (int)(base + 0.5);
                base += mult;
            }

            // Now apply the changes (stretch the values)
            int r, g, b;

            for(int p = 0; p < condition; p++) {

                if(filter_value == 2) {
                    r = hist[(raster[p] >> 16) & 0xff];
                    g = (raster[p] >> 8) & 0xff;
                    b = raster[p] & 0xff;
                }
                else if(filter_value == 3) {
                    r = (raster[p] >> 16) & 0xff;
                    g = hist[(raster[p] >> 8) & 0xff];
                    b = raster[p] & 0xff;
                }
                else {
                    r = (raster[p] >> 16) & 0xff;
                    g = (raster[p] >> 8) & 0xff;
                    b = hist[raster[p] & 0xff];
                }

                raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
            }
        }
        else if(filter_value == 5) { //Hue
            int hist[] = new int[1025];

            int i;
            double mult, count, percentage, next_percentage;
            int low = 0, high = 0;

            int hist_len = hist.length - 1;

            int r, g, b;

            // Fill the histogram by counting the number of pixels with each value
            for(int p = 0; p < condition; p++) {
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                hist[(int)(res[0] * hist_len + 0.5)]++;
            }

            count = image_size * image_size;

            // Determine the low input value
            next_percentage = hist[0] / count;
            for(i = 0; i < hist_len; i++) {
                percentage = next_percentage;
                next_percentage += hist[i + 1] / count;
                if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                    //low = i;//i+1; This is a deviation from the way The GIMP does it
                    low = i + 1;
                    // that prevents any change in the image if it's
                    // already optimal
                    break;
                }
            }
            // Determine the high input value
            next_percentage = hist[hist_len] / count;
            for(i = hist_len; i > 0; i--) {
                percentage = next_percentage;
                next_percentage += hist[i - 1] / count;
                if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                    //high = i;//i-1; This is a deviation from the way The GIMP does it
                    high = i - 1;
                    // that prevents any change in the image if it's
                    // already optimal
                    break;
                }
            }

            // Turn the histogram into a look up table to stretch the values
            mult = ((double)hist_len) / (high - low);

            for(i = 0; i < low; i++) {
                hist[i] = 0;
            }

            for(i = hist_len; i > high; i--) {
                hist[i] = hist_len;
            }

            double base = 0.0;

            for(i = low; i <= high; i++) {
                hist[i] = (int)(base + 0.5);
                base += mult;
            }

            // Now apply the changes (stretch the values)
            for(int p = 0; p < condition; p++) {
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                double temp = hist[(int)(res[0] * hist_len + 0.5)] / ((double)hist_len);

                raster[p] = Color.HSBtoRGB((float)temp, res[1], res[2]);
            }

        }
        else if(filter_value == 6) { //Saturation
            int hist[] = new int[1025];

            int i;
            double mult, count, percentage, next_percentage;
            int low = 0, high = 0;

            int hist_len = hist.length - 1;

            int r, g, b;

            // Fill the histogram by counting the number of pixels with each value
            for(int p = 0; p < condition; p++) {
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                hist[(int)(res[1] * hist_len + 0.5)]++;
            }

            count = image_size * image_size;

            // Determine the low input value
            next_percentage = hist[0] / count;
            for(i = 0; i < hist_len; i++) {
                percentage = next_percentage;
                next_percentage += hist[i + 1] / count;
                if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                    //low = i;//i+1; This is a deviation from the way The GIMP does it
                    low = i + 1;
                    // that prevents any change in the image if it's
                    // already optimal
                    break;
                }
            }
            // Determine the high input value
            next_percentage = hist[hist_len] / count;
            for(i = hist_len; i > 0; i--) {
                percentage = next_percentage;
                next_percentage += hist[i - 1] / count;
                if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                    //high = i;//i-1; This is a deviation from the way The GIMP does it
                    high = i - 1;
                    // that prevents any change in the image if it's
                    // already optimal
                    break;
                }
            }

            // Turn the histogram into a look up table to stretch the values
            mult = ((double)hist_len) / (high - low);

            for(i = 0; i < low; i++) {
                hist[i] = 0;
            }

            for(i = hist_len; i > high; i--) {
                hist[i] = hist_len;
            }

            double base = 0.0;

            for(i = low; i <= high; i++) {
                hist[i] = (int)(base + 0.5);
                base += mult;
            }

            // Now apply the changes (stretch the values)
            for(int p = 0; p < condition; p++) {
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                double temp = hist[(int)(res[1] * hist_len + 0.5)] / ((double)hist_len);

                raster[p] = Color.HSBtoRGB(res[0], (float)temp , res[2]);
            }

        }
        
    }

    public static void filterColorChannelMixing(BufferedImage image, int filter_value) {
        
        filter_value = filter_value + 1 + filter_value / 3;

        int[] matrix = {
            1, 0, 0,
            0, 1, 0,
            0, 0, 1,};

        matrix[filter_value] = 1;

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int r, g, b;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            r = ColorSpaceConverter.clamp(matrix[0] * r + matrix[1] * g + matrix[2] * b);
            g = ColorSpaceConverter.clamp(matrix[3] * r + matrix[4] * g + matrix[5] * b);
            b = ColorSpaceConverter.clamp(matrix[6] * r + matrix[7] * g + matrix[8] * b);

            raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }

    }

    public static void filterColorChannelScaling(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int r, g, b;

        double rFactor = 2 * ((int)(filter_value / 1000000.0)) / 100.0;
        double gFactor = 2 * ((int)(((int)(filter_value % 1000000.0)) / 1000)) / 100.0;
        double bFactor = 2 * ((int)(((int)(filter_value % 1000000.0)) % 1000)) / 100.0;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            r = ColorSpaceConverter.clamp((int)(r * rFactor));
            g = ColorSpaceConverter.clamp((int)(g * gFactor));
            b = ColorSpaceConverter.clamp((int)(b * bFactor));

            raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }
    }

    public static void filterDither(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int r, g, b;
        
        int[] matrix = null;
        
        int dither_mat_number = ((int)(filter_value / 1000.0));
        int levels = ((int)(filter_value % 1000.0));
        
        
        switch (dither_mat_number) {
            case 0:
                matrix = ditherMagic2x2Matrix;
                break;
            case 1:
                matrix = ditherOrdered4x4Matrix;
                break;
            case 2:
                matrix = ditherOrdered6x6Matrix;
                break;
            case 3:
                matrix = ditherOrdered8x8Matrix;
                break;
            case 4:
                matrix = ditherMagic4x4Matrix;
                break;
            case 5:
                matrix = ditherCluster3Matrix;
                break;
            case 6:
                matrix = ditherCluster4Matrix;
                break;
            case 7:
                matrix = ditherCluster8Matrix;
                break;
            case 8:
                matrix = ditherLines4x4Matrix;
                break;
            case 9:
                matrix = dither90Halftone6x6Matrix;
                break;
        }
    

        int condition = image_size * image_size;

        /**
         * Init
         */
        int rows, cols;
        rows = cols = (int)Math.sqrt(matrix.length);
        int[] map = new int[levels];
        for(int i = 0; i < levels; i++) {
            int v = 255 * i / (levels - 1);
            map[i] = v;
        }
        int[] div = new int[256];
        int[] mod = new int[256];
        int rc = (rows * cols + 1);
        for(int i = 0; i < 256; i++) {
            div[i] = (levels - 1) * i / 256;
            mod[i] = i * rc / 256;
        }
        /**
         * *
         */

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            int col = (p % image_size) % cols;
            int row = (p / image_size) % rows;
            int v = matrix[row * cols + col];

            r = map[mod[r] > v ? div[r] + 1 : div[r]];
            g = map[mod[g] > v ? div[g] + 1 : div[g]];
            b = map[mod[b] > v ? div[b] + 1 : div[b]];

            raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }

    }

    public static void filterHSBcolorChannelScaling(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int r, g, b;

        double hFactor = 2 * ((((int)(filter_value / 1000000.0)) / 100.0) - 0.5);
        double sFactor = 2 * ((((int)(((int)(filter_value % 1000000.0)) / 1000)) / 100.0) - 0.5);
        double bFactor = 2 * ((((int)(((int)(filter_value % 1000000.0)) % 1000)) / 100.0) - 0.5);

        int condition = image_size * image_size;

        float[] hsb = new float[3];

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            Color.RGBtoHSB(r, g, b, hsb);
            hsb[0] += hFactor;
            while(hsb[0] < 0) {
                hsb[0] += Math.PI * 2;
            }
            hsb[1] += sFactor;
            if(hsb[1] < 0) {
                hsb[1] = 0;
            }
            else if(hsb[1] > 1.0) {
                hsb[1] = 1.0f;
            }
            hsb[2] += bFactor;
            if(hsb[2] < 0) {
                hsb[2] = 0;
            }
            else if(hsb[2] > 1.0) {
                hsb[2] = 1.0f;
            }
            int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);

            raster[p] = 0xff000000 | (rgb & 0xffffff);
        }
    }

    public static void filterPosterize(BufferedImage image, int filter_value) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        int[] levels = new int[256];

        int numLevels = filter_value;

        for(int i = 0; i < levels.length; i++) {
            levels[i] = 255 * (numLevels * i / 256) / (numLevels - 1);
        }

        int r, g, b;

        for(int p = 0; p < condition; p++) {
            r = levels[(raster[p] >> 16) & 0xff];
            g = levels[(raster[p] >> 8) & 0xff];
            b = levels[raster[p] & 0xff];

            raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }

    }

    public static void filterSolarize(BufferedImage image) {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        int[] table = new int[256];

        double f;

        for(int i = 0; i < table.length; i++) {
            f = i / 255.0;
            f = f > 0.5 ? 2 * (f - 0.5) : 2 * (0.5 - f);
            table[i] = ColorSpaceConverter.clamp((int)(255 * f));
        }

        int r, g, b;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            raster[p] = 0xff000000 | (table[r] << 16) | (table[g] << 8) | table[b];
        }
    }

    public static void filterGain(BufferedImage image, int filter_value) {

        double gain = (((int)(filter_value / 1000.0)) / 100.0);
        double bias = (((int)(filter_value % 1000.0)) / 100.0);

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        int r, g, b;
        double f;

        int[] table = new int[256];
        for(int i = 0; i < 256; i++) {
            f = i / 255.0f;

            double gain_result = (1.0f / gain - 2.0f) * (1.0f - 2.0f * f);

            f = f < 0.5 ? f / (gain_result + 1.0f) : (gain_result - f) / (gain_result - 1.0f);

            f = f / ((1.0f / bias - 2) * (1.0f - f) + 1);

            table[i] = ColorSpaceConverter.clamp((int)(255 * f));
        }

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            raster[p] = 0xff000000 | (table[r] << 16) | (table[g] << 8) | table[b];
        }

    }

    public static void filterGamma(BufferedImage image, int filter_value) {

        double gamma = filter_value / 100.0 * 3.0;
        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        int r, g, b;

        int[] table = new int[256];
        for(int i = 0; i < 256; i++) {
            int v = (int)((255.0 * Math.pow(i / 255.0, 1.0 / gamma)) + 0.5);
            if(v > 255) {
                v = 255;
            }
            table[i] = v;
        }

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            raster[p] = 0xff000000 | (table[r] << 16) | (table[g] << 8) | table[b];
        }
    }

    public static void filterExposure(BufferedImage image, int filter_value) {

        double exposure = filter_value / 100.0 * 5.0;

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        int r, g, b;
        double f;

        int[] table = new int[256];
        for(int i = 0; i < 256; i++) {
            f = i / 255.0f;

            f = 1 - Math.exp(-f * exposure);

            table[i] = ColorSpaceConverter.clamp((int)(255 * f));
        }

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            raster[p] = 0xff000000 | (table[r] << 16) | (table[g] << 8) | table[b];
        }

    }
    
    private static float[] createGaussianKernel(int length, double weight) {
        float[] gaussian_kernel = new float[length * length];
        double sumTotal = 0;

        int kernelRadius = length / 2;
        double distance = 0;

        double calculatedEuler = 1.0 / (2.0 * Math.PI * weight * weight);

        float temp;
        for(int filterY = -kernelRadius; filterY <= kernelRadius; filterY++) {
            for(int filterX = -kernelRadius; filterX <= kernelRadius; filterX++) {
                distance = ((filterX * filterX) + (filterY * filterY)) / (2 * (weight * weight));
                temp = gaussian_kernel[(filterY + kernelRadius) * length + filterX + kernelRadius] = (float)(calculatedEuler * Math.exp(-distance));
                sumTotal += temp;
            }
        }

        for(int y = 0; y < length; y++) {
            for(int x = 0; x < length; x++) {
                gaussian_kernel[y * length + x] = (float)(gaussian_kernel[y * length + x] * (1.0 / sumTotal));
            }
        }
        
        return gaussian_kernel;

    }

}
