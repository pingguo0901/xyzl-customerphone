package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MalaysiaGuideScreen(onBack: () -> Unit = {}) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, "返回")
            }
            Spacer(Modifier.width(8.dp))
            Column {
                Text("🇲🇾 马来西亚跨境手册", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("西马+东马通用 | 航空/陆路新马关卡双适用 2026最新", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            }
        }

        // 一、入境必做
        GuideSection("一、入境必做", "缺一不可，会被遣返/扣关") {
            GuideSubSection("证件硬性要求") {
                GuideBullet("护照有效期 ≥6 个月，空白页 ≥2 页，无以色列签证/出入境记录（有以色列记录直接拒绝入境）")
                GuideBullet("提前在线填写 MDAC马来西亚电子入境卡（打印纸质回执，东马沙巴/砂拉越必须纸质版，不能只用电子版）")
                GuideBullet("往返返程机票行程单、全程酒店预订单（移民局随机抽查）")
                GuideBullet("资金证明：建议随身携带等值 1000美元以上 现金/银行卡流水备查")
            }

            GuideSubSection("通关流程") {
                GuideBullet("航空：下机→移民局盖章→取行李→海关抽查（超额度走红色申报通道）")
                GuideBulletColor("陆路（新加坡→马来西亚：兀兰/大士关卡）：先出境新加坡，再入境马来西亚，必须两次盖章，不要漏掉出境章", Color(0xFFFF9800))
            }

            GuideSubSection("提前办理许可物品（没有许可绝对不能带）") {
                GuideBullet("无人机：必须在CAAM马来西亚民航局线上注册获批，无许可直接没收罚款")
                GuideBullet("大功率对讲机、电台设备：需要MCMC通讯局执照")
                GuideBullet("处方药（安眠药、精神类、激素药）：英文医生处方+病历+药品原包装")
                GuideBullet("活体宠物、动植物、带壳鸡蛋：农业部进口许可")
            }
        }

        // 二、必带实用物品
        GuideSection("二、必带实用物品", "通关&生活必备") {
            GuideBullet("转换插头：英标三方插（Type G）")
            GuideBullet("常用非管制西药：布洛芬、氯雷他定、碘伏、创可贴（保留原盒，不要拆散装）")
            GuideBullet("手机下载：Touch n Go eWallet，MDAC申报页面")
            GuideBullet("少量无肉纯零食：饼干、糖果、纯巧克力、真空无肉挂面、干茶叶")
        }

        // 三、合法可以携带
        GuideSection("三、✅ 合法可以携带", "免税额度内无需申报") {
            GuideBullet("年满18岁才可享受免税，未成年人无烟酒免税额度")
            GuideBulletColor("酒类：最多1L任意烈酒/红酒/啤酒（超额必须缴税申报）", Color(0xFF4CAF50))
            GuideBulletColor("香烟：严格限制 19根香烟（没有整包免税额度），雪茄25g以内，烟草制品超额重罚", Color(0xFFFF9800))
            GuideBullet("个人商品：陆路入境免税额度 RM500；航空入境 RM1000")
            GuideBullet("电子产品：个人自用手机2台、笔记本1台、手表相机（仅限自用，大量全新电子产品一定要申报）")
            GuideBullet("食品：无肉干货、花茶、冰糖、方便面（无肉油包）、密封饼干")
        }

        // 四、绝对禁止携带
        GuideSection("四、❌ 绝对禁止携带入境", "零容忍，没收+高额罚款甚至刑事责任") {
            GuideSubSection("违禁药品") {
                GuideBulletColor("毒品、大麻CBD、含麻黄碱（新康泰克、复方甘草片）、吗啡、士的宁类中成药、止咳水；贩毒最高死刑", Color(0xFFF44336))
            }

            GuideSubSection("烟草雾化类") {
                GuideBulletColor("电子烟、烟油、加热烟完全禁止，一根电子烟都不能带", Color(0xFFF44336))
            }

            GuideSubSection("食品类（检疫红线）") {
                GuideBulletColor("所有生鲜肉、猪肉制品、肉干、香肠、卤味、咸鸭蛋、生鲜水果、蔬菜、种子、土壤、多肉植物、燕窝、海参", Color(0xFFFF9800))
                GuideBulletColor("穆斯林国家严查猪肉食物", Color(0xFFFF9800))
            }

            GuideSubSection("敏感物品") {
                GuideBulletColor("带有以色列六芒星、以色列国旗、以色列标识的衣物书籍", Color(0xFFF44336))
                GuideBulletColor("宗教煽动、色情、盗版光碟/软件、假冒名牌", Color(0xFFF44336))
            }

            GuideSubSection("武器危险物品") {
                GuideBulletColor("弹簧刀、指节套、仿真玩具枪、BB枪、电击棍、胡椒喷雾、烟花爆竹、防弹衣、武士刀", Color(0xFFF44336))
            }

            GuideSubSection("其他") {
                GuideBulletColor("食人鲳活体、未经许可的无线电设备、大米稻米、有毒化学品", Color(0xFFF44336))
            }
        }

        // 五、强制申报
        GuideSection("五、⚠️ 必须主动海关申报", "走红色通道，隐瞒=巨额罚款+扣押") {
            GuideBulletColor("现金/支票/本票：总金额超过等值10000美元（马币+外币合计）强制申报", Color(0xFFFF9800))
            GuideBulletColor("烟酒超出免税额度（1L酒、19根烟）", Color(0xFFFF9800))
            GuideBulletColor("全新未拆封大批量电子产品、奢侈品手表珠宝（避免判定为商用走私）", Color(0xFFFF9800))
            GuideBulletColor("无人机、专业摄像机、长焦镜头、影视拍摄设备", Color(0xFFFF9800))
            GuideBulletColor("处方药、大量中成药、草本药材包", Color(0xFFFF9800))
            GuideBulletColor("贵重古董、文物、贵金属", Color(0xFFFF9800))
            GuideBulletColor("商业货物、样品（个人行李不可以夹带商用货品）", Color(0xFFFF9800))
        }

        // 六、新马陆路跨境额外规则
        GuideSection("六、新马陆路跨境额外规则", "新加坡带去大马") {
            GuideBulletColor("从新加坡买的肉干、生鲜蛋、生蚝、肉类，一律不许带入马来西亚", Color(0xFFFF9800))
            GuideBulletColor("马来西亚香烟管控极严，不要在新加坡买烟过关", Color(0xFFFF9800))
            GuideBulletColor("液体护肤品单件超过100ml建议托运，陆路关卡也会开箱查验", Color(0xFFFF9800))
        }

        Spacer(Modifier.height(80.dp))
    }
}
