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
fun SingaporeGuideScreen(onBack: () -> Unit = {}) {
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
                Text("🇸🇬 新加坡跨境手册", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("2026最新 ICA 版本 | 行人/行李跨境通用", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            }
        }

        // 一、入境必知必做
        GuideSection("一、入境必知必做", "缺一不可，会被拒绝入境、拉黑入境记录") {
            GuideSubSection("硬性证件要求") {
                GuideBullet("护照有效期必须≥6个月，至少预留3页空白签证页，破损、涂改护照无法通关")
                GuideBullet("提前3天内（包含抵达当天）在 MyICA APP / ICA 官网填写 SG Arrival Card 电子入境申报卡，纸质表单无效；过境不入境、新加坡本地人/PR走陆路可豁免，外籍游客必须填写")
                GuideBullet("提前准备好：30天内返程机票订单、全程酒店预订单（入住亲友家需备好对方地址+联系方式）")
                GuideBullet("移民官随机抽查资金证明：建议备好等值新币现金或银行卡流水，证明可覆盖旅行开销")
                GuideBullet("过去6天内到访过黄热病疫区国家，必须携带黄热病疫苗国际证书，否则禁止入境")
                GuideBullet("禁止委托陌生人帮带行李，一旦查出违禁品，携带者承担全部法律责任")
            }

            GuideSubSection("通关双通道规则（强制区分）") {
                GuideBulletColor("绿色通道：无违禁品、无超额应税商品、无管制物品才可通行（依然会随机开箱抽查）", Color(0xFF4CAF50))
                GuideBulletColor("红色申报通道：只要有管制物品、超额贵重物品、大额现金，必须主动走红色申报口；瞒报查获最高罚款10000新元，甚至刑事起诉", Color(0xFFF44336))
            }

            GuideSubSection("需要提前申请许可才能带入（无证一律没收扣押）") {
                GuideBullet("活体动植物、生鲜食材、宠物、植物种子、土壤肥料：必须向 AVA 新加坡农粮局申请进口许可")
                GuideBullet("对讲机、无线电设备、信号发射器、大功率拍摄设备：需要 IMDA 通讯局批文")
                GuideBullet("镇静类、抗抑郁、精神管制处方药：附带英文医院处方+药品原包装，散装药不予认可")
                GuideBullet("刀具、防身器具、仿制武器：必须向警局 PRD 部门申请准入执照")
            }
        }

        // 二、出行必带实用物品
        GuideSection("二、出行必带实用物品", "无通关风险") {
            GuideBullet("转换插头：英标 Type G 三方插座")
            GuideBullet("常用非管制西药：布洛芬、过敏药、外伤药膏（务必保留原装药盒）")
            GuideBullet("支付工具：国际信用卡、跨境支付宝、Singtel Dash、PayNow（本地主流支付）")
            GuideBullet("少量无肉类密封零食、独立包装茶叶、纯黑巧克力")
            GuideBullet("数据线、个人自用电子设备（不要带大量全新未拆电子产品）")
        }

        // 三、完全可以携带
        GuideSection("三、✅ 完全可以携带", "免税额度内，无需申报") {
            GuideBullet("仅限年满18周岁成年人享受免税，未成年人无烟酒免税额度")
            GuideBulletColor("酒类（境外停留超过48小时才可享受）：烈酒1L 或 葡萄酒/啤酒2L，二选一不可叠加", Color(0xFF4CAF50))
            GuideBullet("日常个人用品：护肤品、洗护用品（液体单件超过100ml建议托运）")
            GuideBullet("自用电子产品：2台手机、1台笔记本电脑、手表、相机（个人使用痕迹明显即可）")
            GuideBullet("食品（仅限少量自用）：无肉饼干、硬糖、干面条（无肉油包）、真空花茶、无添加果酱")
            GuideBullet("免税购物额度：离境新加坡境外满48小时，个人免税额度 500 新元")
        }

        // 四、绝对禁止携带
        GuideSection("四、❌ 绝对禁止携带入境", "零容忍，没有特例，查获直接没收+高额罚款+案底") {
            GuideSubSection("烟草尼古丁类（新加坡法律最严格）") {
                GuideBulletColor("电子烟、烟油、加热烟、嚼烟、鼻烟、水烟、尼古丁贴片全部违法", Color(0xFFF44336))
                GuideBulletColor("香烟没有任何免税额度，哪怕1根也必须申报缴税，私带香烟会被高额罚款", Color(0xFFF44336))
            }

            GuideSubSection("违禁药品（最高可判处死刑）") {
                GuideBulletColor("毒品、大麻、CBD 全系列产品一律违法", Color(0xFFF44336))
                GuideBulletColor("含麻黄碱、可待因、吗啡的药品：复方甘草片、部分国产止咳水、部分感冒药、中成药液剂", Color(0xFFF44336))
                GuideBulletColor("散装中药粉、来路不明草本药膏禁止入境", Color(0xFFF44336))
            }

            GuideSubSection("食品检疫红线（马来西亚跨境最容易踩坑）") {
                GuideBulletColor("所有肉类制品：大马肉干、腊肉、香肠、肉松、沙爹、含肉月饼、卤味熟食", Color(0xFFFF9800))
                GuideBulletColor("生鲜海鲜、生蚝、贝类、新鲜榴莲、山竹、各类新鲜果蔬", Color(0xFFFF9800))
                GuideBulletColor("皮蛋、咸蛋、卤蛋只能原厂盒装普通鸡蛋最多携带30颗，其余蛋类全部禁止", Color(0xFFFF9800))
                GuideBulletColor("燕窝、海参、野生动物食材完全禁止", Color(0xFFFF9800))
            }

            GuideSubSection("武器危险器具（管制违禁品）") {
                GuideBulletColor("弹簧刀、重力甩刀、飞镖、指节铜套、弹弓、电击器、胡椒喷雾", Color(0xFFF44336))
                GuideBulletColor("仿真枪、软弹玩具枪、手枪造型打火机、武士刀、各类暗器", Color(0xFFF44336))
                GuideBulletColor("防弹衣、手铐、警用装备、烟花爆竹", Color(0xFFF44336))
            }

            GuideSubSection("侵权与违规出版物") {
                GuideBulletColor("盗版软件、光碟、高仿假冒名牌商品", Color(0xFFF44336))
                GuideBulletColor("色情刊物、煽动性政治资料、仇恨类印刷品", Color(0xFFF44336))
            }

            GuideSubSection("其他违禁品") {
                GuideBulletColor("口香糖（仅有医院开具处方的医用口香糖合法，普通口香糖一点都不能带）", Color(0xFFF44336))
                GuideBulletColor("干扰器、偷拍设备、针孔摄像头（无许可属于刑事违规）", Color(0xFFF44336))
            }
        }

        // 五、强制申报
        GuideSection("五、⚠️ 强制必须主动申报", "走红色通道，隐瞒会被财产没收") {
            GuideBulletColor("现金、外币、支票、不记名票据合计总额 ≥ 20000 新元，必须走 CBCRR 现金申报系统", Color(0xFFFF9800))
            GuideBulletColor("超额烟酒、超出500新元免税额度的奢侈品（黄金、钻戒、名牌手表、大量全新电子产品）", Color(0xFFFF9800))
            GuideBulletColor("无人机、长焦专业镜头、航拍设备、商用拍摄器材", Color(0xFFFF9800))
            GuideBulletColor("大批量中草药、处方药、镇静类药物", Color(0xFFFF9800))
            GuideBulletColor("任何肉类、蛋类、动植物加工制品（哪怕真空包装也要申报检疫）", Color(0xFFFF9800))
            GuideBulletColor("无线电设备、信号设备、商用样品货物（个人行李不能夹带商品货物）", Color(0xFFFF9800))
        }

        // 六、马来西亚步行跨境
        GuideSection("六、马来西亚步行跨境新加坡专属额外注意事项", "行人关卡专用") {
            GuideBulletColor("不要从新山携带榴莲、生鲜水果、肉干、街边熟食过关，开箱率极高", Color(0xFFFF9800))
            GuideBulletColor("不要拆分现金躲避2万新元申报线，关卡配备现金探测设备", Color(0xFFFF9800))
            GuideBulletColor("不要网购零食夹带肉制品、咸蛋、散装中药，会直接没收并罚款", Color(0xFFFF9800))
            GuideBulletColor("液态中药、膏状草药尽量不要携带，海关大概率扣押查验", Color(0xFFFF9800))
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun GuideSection(title: String, subtitle: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun GuideSubSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.height(6.dp))
        content()
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun GuideBullet(text: String) {
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text("• ", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
        Text(text, fontSize = 13.sp, lineHeight = 19.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun GuideBulletColor(text: String, color: Color) {
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text("• ", fontSize = 14.sp, color = color)
        Text(text, fontSize = 13.sp, lineHeight = 19.sp, color = color)
    }
}
