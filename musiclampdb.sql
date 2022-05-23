-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- 主機： localhost
-- 產生時間： 2020 年 01 月 10 日 03:49
-- 伺服器版本： 10.3.15-MariaDB
-- PHP 版本： 7.1.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `musiclampdb`
--

-- --------------------------------------------------------

--
-- 資料表結構 `heartanalysis`
--

CREATE TABLE `heartanalysis` (
  `analysis_id` int(10) NOT NULL,
  `analysis_result` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `analysis_suggest` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `analysis_content` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `analysis_url` varchar(60) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 傾印資料表的資料 `heartanalysis`
--

INSERT INTO `heartanalysis` (`analysis_id`, `analysis_result`, `analysis_suggest`, `analysis_content`, `analysis_url`) VALUES
(1, 'low', '容易低落，該如何提振心情', '　　運動是解決之道，心情不好的時候最不想運動了，不過運動已證實能提振情緒，而且只要運動 10-15分鐘，就能讓人心情變好，固定做運動對於治療輕度憂鬱和焦慮，效果和藥物或心理治療一樣好。騎自行車、游泳、散步或跳舞這類節律運動，對改善心情的效果最好，上運動課程或參加團體運動也很不錯，因為無論認識新朋友或運動本身都有好處。\r\n\r\n　　睡個好覺，要維持精神好，就一定要好好睡覺，睡眠不足會影響處理壓力的能力。大部分的人每天晚上需要睡7至8小時，要確定自己每天都能睡足夠的時間，這樣早上起床時才會覺得神清氣爽。\r\n\r\n　　飲食均衡，科學家認為，腦部需要40到50種不同的營養素才能正常運作。要獲得全部所需的營養素，最好的方法就是均衡飲食，而且要吃許多蔬果，喝大量的水補充身體所需水份。此外，要確定攝取足夠的鐵和維他命B群，缺乏這兩種營養素會讓人覺得疲倦，心情沮喪。紅肉和魚類是良好的鐵質來源，如果不吃肉的話，應該多吃全穀類、葉菜類和豆類來補強。最好的維他命B群來源是肉類，其他也不錯的來源包括蘆筍、青花椰菜、香蕉、牛奶、杏果乾和糙米。', '心情沮喪時該怎麼辦 (Cigna Corporation發布於康健心理健康文章)'),
(2, 'calm', '平靜的生活，該如何提升快樂的感覺', '　　不要太積極得尋找快樂，快樂，不能過度索取，快樂的感覺太可口了，你會一直很渴望。就如同對同一款巧克力的喜愛，第一次吃覺得非常美味，但是越吃越多，美味的感覺都不像第一次那麼強烈。因為神經會對新奇的事物產生反應，多餘的快樂感覺，就會被自動忽視。\r\n\r\n　　仔細品味生活每一刻快樂的來源是『專注當下』，增加快樂的方式還有回憶，回想曾經快樂的時光，當下也會感覺愉快。雖然這是所有人都知道的事情，但對於這個理論，科學家有更深入的研究結果─快樂的來源其中之一是來自於專注當下，例如：當你聽喜歡的音樂的時候，閉上眼睛仔細品味。讓自己和喜歡的事情合為一體。', '每天讓自己快樂的14種方法 (2017-05-02 國家網路醫藥發佈)'),
(3, 'excitement', '太過激動，如何適時緩和激動情緒', '美國健康網站Webmd提供了以下幾個好辦法能幫您改善憤怒情緒：\r\n- 深呼吸，開始數數\r\n- 遠離是非，多做運動\r\n- 與自己交談\r\n- 寫下不好的心情，然後把它扔掉\r\n- 與人交談\r\n- 改變想法\r\n- 擁抱親人', '七種方法，立即舒緩憤怒情緒 (2014-10-06更新 秦飛)'),
(4, 'office', '集中精神，辦公時容易分心', '克服「容易分心」的3個小工具：\r\n1.To-do-list\r\n預先空出時間來安排優先順序，問自己該把注意力放在哪裡，先做最重要的事。\r\n\r\n2.設定目標工時\r\n回想學生時代徹夜趕報告的認真投入，在有時間壓力的情況下，我們往往能發揮出潛在的專注力。\r\n\r\n3.將工作目標數值化\r\n數值化能夠幫助工作者確定「自己該做的事，距離目標還有多遠」，也才能據此規畫時間、定出目標。\r\n　　專注力可以透過鍛鍊達成，當你省去浪費時間的事，就可使工作變得有效率。', '如何戒掉「容易分心」的壞習慣?縮小目標、設定期限，一次只做一件事 (2012-01-17 張玉琦整理)'),
(5, 'relax', '真正的休息，如何真正做到放鬆', '　　改變飲食，睡個好覺，很多人都會陷入一個怪圈：上班睡不醒，放假睡不著。這可能與飲食習慣有關。美國賓夕法尼亞大學研究發現，睡眠好的人飲食更豐富、均衡。因此，想要睡個好覺，應先從飲食入手。\r\n\r\n　　改變習慣，慢速生活，如果你平時總是忙碌、緊張，假期裡不妨讓自己慢下來。\r\n　　\r\n　　改變壓力，迎接挑戰，職場競爭、家庭瑣事、孩子教育……不少人雖然外表堅強，內心可能早已不堪一擊。利用假期，給自己心靈洗個澡吧，好好想想以下幾個問題，沒準能幫你趕走心中的負能量，先要學會自信，有了自信才能抓住機會、克服困難、堅持到底。但自信不等於自大，要建立在知識、技能之上。', '越休息越累?做到這幾點才能真正放鬆 (2016-02-03 訊 阿波羅新聞網)'),
(6, 'morning', '早晨的陽光，早上總是提不起勁', '　　從早上懶得起床、懶得上班，到不想做事、不想面對接踵而來的壓力，我們總是懈怠，在可以逃避的情況下，能夠少件差事就是幸福。但，我們肯定曾因為自己的懶惰，讓工作沒效率、進度被延誤，唯有跳脫這個桎梏，才能活出嶄新人生。\r\n\r\n　　《商業內幕》（Business Insider）彙整了專家之意見，建議10種能拋下懶惰的方式，也許你會發現工作更有意義。\r\n1、隨時設定一個10分鐘的鬧鐘\r\n2、把簡單的工作留到明天早晨再做\r\n3、保持運動生活\r\n4、開放自己的工作環境\r\n5、找一個合作夥伴\r\n6、化妝、打扮自己\r\n7、把你正面對的問題寫出來\r\n8、做你正在考慮的事\r\n9、遵循「2分鐘法則」\r\n10、保持工作的熱情', '做事總提不起勁?這10種方法能讓你拋下懶惰 (2016-07-28 魯皓平撰寫)'),
(7, 'night', '夜晚的平靜，如何提升睡眠品質', '　　日本睡眠醫學專家梶村認為，晚上遲遲無法入睡的根本原因在於「準備不足」。天黑了以後變得想睡覺是人類生理時鐘的自然規則。但現代生活，夜晚和白天一樣燈火通明、充滿刺激，讓身體難以遵循生理上的睡眠提示。因此，必須有意識地準備好容易入睡的環境才行。梶村接受雜誌《THE 21》採訪，提供了5個快速入眠處方籤。\r\n\r\n　　睡前兩小時改以間接照明，只要房間變暗，人體就會開始分泌能幫助入睡的退黑激素。睡前改以間接照明，讓室內變暗，自然就會產生睡意。\r\n\r\n　　晚餐在睡前3小時結束，消化器官若在睡眠時持續運作，會妨礙人體進入深層睡眠，因此晚餐最晚應該在睡前3小時吃完。但如果空腹睡不著的話，不用強忍飢餓，只要吃點起司、水果即可。\r\n\r\n　　伸展運動，身體僵硬是妨礙睡眠的主要原因之一。只要躺在床上伸展5分鐘，促進血液循環，就能讓身體深部體溫下降，快速入睡。深部體溫指的是，入睡前身體會發熱，讓身體散熱有助入眠。\r\n\r\n　　電腦和智慧型手機是睡眠的大敵，一直玩電腦、手機接受資訊，會使大腦處於興奮狀態；此外，明亮的螢幕也會刺激交感神經，消除睡意。最好睡前兩小時就切掉電源。下班回家後可改用聽音樂、讀書的方式讓大腦準備休息。\r\n\r\n　　周末補眠不超過兩小時，周末睡太多反而會打亂睡眠韻律，等到周一要上班的時候更痛苦。補眠不是一昧增加睡眠時間，應該是講究睡眠品質。良好的深層睡眠才能將自己從白天的渾渾噩噩中解放出來。', '翻來覆去睡不著?5招提升睡眠品質，給你一夜好眠 (2013-06-11 張玉琦整理)');

-- --------------------------------------------------------

--
-- 資料表結構 `knowledge`
--

CREATE TABLE `knowledge` (
  `qa_id` int(10) NOT NULL,
  `qa_type` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `qa_question` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `qa_answer` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `qa_url` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `qa_image` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 傾印資料表的資料 `knowledge`
--

INSERT INTO `knowledge` (`qa_id`, `qa_type`, `qa_question`, `qa_answer`, `qa_url`, `qa_image`) VALUES
(1, 'light', '情緒低落時，為何要使用橙色燈光?', '　　橙色是一種有意思的顏色。它並不是簡單地與某一種感覺像關聯，但是它能從很多方面影響我們。首先，我們很容易把橙色跟溫暖聯繫在一起。如果一個房間裡面被裝修成橙色，那麼我們一定會有一種房間溫度高於外面溫度的錯覺。橙色能放鬆我們的肌肉，研究表明橙色能在激素水平上影響我們的肌肉功能，從而大幅度放鬆我們的肌肉。', '10種顏色影響我們心情的方式 (2017-06-19 由腦殼知識發表)', 'R.drawable.light_img1'),
(2, 'light', '情緒平靜時，為何要使用淡藍燈光?', '　　藍色被認為是紅色的反面。它是除了紅色之外研究最多的顏色。淺藍色是平靜和注意力集中的顏色。人們發現淺藍色會降低血壓，這就是為什麼在醫院裡，護士和醫生都穿淺藍色的衣服。這也是為什麼醫院裡面很多地方都是淺藍色的裝修。', '10種顏色影響我們心情的方式 (2017-06-19 由腦殼知識發表)', 'R.drawable.light_img2'),
(3, 'light', '情緒激動時，為何要使用粉紅燈光?', '　　粉紅色是一個安靜的小嬰兒的顏色。粉紅色通常是一些小混混短褲的顏色。粉紅色是一種極其有意思的顏色。它對人有一種肉眼可以觀察的影響。心理學家把粉紅色對行為的影響這種現象稱為「粉紅色監獄」。\r\n　　一項研究發現當犯人被放置在明亮的粉紅色環境中的時候，他們立即會變得更加平和，他們的肌肉會立即放鬆下來。他們的不安和沮喪也顯著降低。現在瑞士有20%的監獄為不守規矩的犯人至少保留了一間粉紅色的牢房。', '10種顏色影響我們心情的方式 (2017-06-19 由腦殼知識發表)', 'R.drawable.light_img3'),
(4, 'light', '辦公或讀書時，適合什麼燈光?   ', '　　研究人員發現，白色最終會導致無聊感。所以裝修成白色的空間比其他顏色的空間更容易讓我們無法長久地吸引我們的注意力，因此可以提高我們自己的思維空間，把我們從環境中分離出來。這就是為什麼一些研究人員建議一些線下的商店最好裝修得顏色豐富多彩一些，而不是單調枯燥的全白色。', '10種顏色影響我們心情的方式 (2017-06-19 由腦殼知識發表)', 'R.drawable.light_img4'),
(5, 'light', '放鬆休息時，適合什麼燈光?   ', '　　綠色是森林的顏色，是大自然的背景色。綠色是很多手機打電話、接聽電話的按鈕顏色，是四種馬克筆的顏色之一。心理學家發現綠色可以增加我們的創造力。他們還發現綠色跟很多複雜的思考過程和更高層次的思想有關，同時綠色也意味著放鬆、內斂的注意力和平靜的行為。\r\n', '10種顏色影響我們心情的方式 (2017-06-19 由腦殼知識發表)', 'R.drawable.light_img5'),
(6, 'light', '早晨與夜晚時，分別適合什麼燈光?', '　　科學上就認為：晚上要照黃光，對健康比較好。\r\n　　英國學者Thapan的研究指出，若晚上照的燈光色溫愈高，會影響褪黑激素分泌，進而引發疾病。國泰醫院眼科醫師梁怡珈也表示，白光容易造成眼睛黃斑部的病變，如白內障。\r\n　　根據研究的說法，若在晚上還是照跟白天一樣的藍白光，身體會以為還在早上，有些該分泌的激素就不分泌了，情緒上就易受影響。因此建議，晚上需要低色溫值約2700~3500K的燈光，也就是黃光；白天適合用高色溫、光色偏藍的白光，約4000~6500K。', '燈泡都一樣?錯!黃光才健康 (2014-06-01 發布)', 'R.drawable.light_img6'),
(7, 'music', '情緒低落時，聽古典樂有效嗎?', '　　養成聽某一種音樂的固定習慣，這類音樂一定是具有治癒系功用的。無論什麼時候都可以拿出來聽，情緒低落時，聽自己慣常聽的音樂，一樣可以使自己靜下心來，理清思路，解決問題，從而走出低落情緒。\r\n　　如古典音樂。大多數流行音樂都帶有很強的意識流色彩，比如有的歌詞寫失戀的，有的旋律過於低沉的，這些都過於單一，反而會影響心情。而古典音樂，它的曲調較豐富，富於變化，包含的感情色彩也更多，更有利於梳理清楚自己的心緒。', '情緒低落時，如何進行自我調整? (2017-03-29 由羅友霸王課發表于心理)', 'R.drawable.music_img1'),
(8, 'music', '情緒激動時，如何舒緩情緒?', '　　許多人以為只要放空大腦，即可達到舒壓目的，但其實在休息時，焦慮與煩心的事，反而容易縈繞腦海揮之不去，即使是徹底安靜的環境，也無法充分放鬆。因此，許多專家建議，不妨藉助音樂力量，透過節拍律動深層療癒身心腦。\r\n　　古希臘數學家畢達哥拉斯(Pythagoras)，在西元前550年就提出了音樂能消除負面能量與情緒的概念，而現代科學家也證實，音樂會透過細胞共振，平靜生物的節律，進而放鬆身體。\r\n　　特別是慢板或柔板、強弱起伏變化較小的古典音樂，有股令人安靜的力量，隨著節奏律動，讓內心變得更沉靜。政大睡眠實驗室的研究就發現，37位受試者在床上聆聽輕柔版古典音樂後，因此幫助放鬆而較易入睡、且睡得較好。', '壓力過大、精神渙散? 用古典樂找回專注力 (2018-04-18 Topsify 提供)', 'R.drawable.music_img2'),
(9, 'music', '什麼音樂有助於辦公時集中精神?', '  你一定聽說過常聽古典樂能夠增進自律、人際溝通能力的研究。不過你知道嗎，只要你學會一套簡單的聆聽方法，聽古典樂也能提升專注力，幫助你工作上不再丟三落四、拖拉延遲。\r\n  提升內在專注，就像是強化內部控制機制，除了更加認識自己，還可能發揮較大的自身潛力。\r\n　　聽見音樂節奏。找個舒適坐姿或臥姿，深呼吸數回直到心情放鬆，接下來專注聆聽音樂節奏；音樂以規律節拍的古典、或巴洛克樂派為主，如巴哈、莫札特的作品，規律有助身體掌握節奏。帶著享受的心情聆聽，保持對節奏的直覺反應，試著讓頭、手、腳、身體跟著打拍子。', '利用音樂強化專注力，聽出工作高效率! (2015-08-31 整理‧編輯楊修)', 'R.drawable.music_img3'),
(10, 'music', '什麼音樂適合在想放鬆時播放?', '　　音樂共振使人體身心、血壓、呼吸達到舒適狀態。\r\n　　聲音是一種振動，而人體本身也是由許多振動系統所構成，如心臟的跳動、腸胃蠕動、大腦波動等。當音樂產生的振動與體內器官共振時，會使人體分泌一種生理活性物質，調節血液流動和神經，讓人富有活力、朝氣蓬勃；同時和諧的節奏也能讓人釋放壓力，讓身心回到平衡狀態，甚至進一步提升免疫能力。\r\n\r\n放鬆身心用 \r\n - 韓德爾F大調豎琴協奏曲(稍緩板)作品四第五號 \r\n- 韓德爾降B大調豎琴與魯特琴協奏曲(稍緩板)作品四第六號\r\n- 韓德爾C大調協奏曲「亞歷山大的盛宴」', '巴洛克音樂 (2011-03-20 00:42 隨意窩分享)', 'R.drawable.music_img4'),
(11, 'music', '早晨時光，最適合聽什麼提神?', '  以羅西尼的《威廉泰爾序曲》來喚醒一整天的元氣活力(早上八點半)\r\n　　《威廉泰爾序曲》是義大利作曲家羅西尼的作品，威廉泰爾是瑞士民間傳說中的英雄人物，曲子分成三個不同的段落，其中最終段的小號旋律為台灣的廣播節目引用作為新聞節目的開場音樂，因此在台灣此旋律非常耳熟能詳(此旋律也非常適合作為手機鬧鈴)。香港的亞洲電視則長年使用以此序曲為賽馬節目主題曲，在香港亦成為賽馬活動的象徵之一。在這裡小紅帽建議從《威廉泰爾序曲》第二段來喚醒一整天的元氣活力。', '打造一整天古典音樂氛圍 從清晨到深夜九首古典情境音樂推薦 (2015-06-02發布 作者洪詩涵)', 'R.drawable.music_img5'),
(12, 'music', '夜晚時光，最適合聽什麼入眠?', '　　古典名曲《愛的禮讚》為婚禮宴會最常見口袋歌單，被視為見證浪漫愛情的最佳代表歌曲之一。作曲者艾爾加爵士在寫作《愛的禮讚》之後，成功得到妻子父母認可，抱回美嬌娘 。而就在他們結婚三週年時，艾爾加又寫下了這一首「弦樂小夜曲」來紀念這段美好的婚姻。這首作品音樂優雅中帶著抒情唯美的主旋律充滿作曲家真摯的情感。讓我們藉由艾爾加爵士其纖細美麗的《小夜曲》，洗滌心靈，趕走一整天的皮辛勞疲憊。\r\n　　鋼琴詩人蕭邦共創作了21首夜曲，每首演奏長度幾乎不超過五分鐘，蕭邦的《夜曲》充分地將鋼琴之美發揮到極致，帶有抑鬱的淡淡憂愁，適合夜深人靜的平靜氛圍。其中許多首夜曲被廣泛應用在戲劇、各類背景情境音樂之中，蕭邦被後人視為將夜曲此一曲種，成功發揚光大的重要作曲家之一。', '打造一整天古典音樂氛圍 從清晨到深夜九首古典情境音樂推薦 (2015-06-02發布 作者洪詩涵)', 'R.drawable.music_img6'),
(13, 'appuse', '如何使用APP連線智慧手環?', '', '', 'R.drawable.appuser_img4'),
(14, 'appuse', '如何手動新增一筆設定?', '', '', 'R.drawable.appuser_img1'),
(15, '', '如何取消或修改手動設定?', '', '', ''),
(16, '', '如何修改使用者基本設定?', '', '', ''),
(17, 'appuse', '選單中的三個音樂按鈕如何使用?', '', '', 'R.drawable.appuser_img5'),
(18, 'appuse', '如何調整音樂大小與燈光亮度?', '', '', 'R.drawable.appuser_img6');

-- --------------------------------------------------------

--
-- 資料表結構 `setting`
--

CREATE TABLE `setting` (
  `setting_id` int(10) NOT NULL,
  `user_acc` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `user_mode` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `user_type` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `user_music` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `user_light` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `start_date` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `start_time` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `user_hr` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 傾印資料表的資料 `setting`
--

INSERT INTO `setting` (`setting_id`, `user_acc`, `user_mode`, `user_type`, `user_music`, `user_light`, `start_date`, `start_time`, `user_hr`) VALUES
(1, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/08/02', '16:02', '86'),
(2, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/08/03', '13:37', ''),
(3, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/08/04', '21:56', ''),
(4, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/08/05', '13:44', ''),
(5, 'mus', 'low', 'preset', '01001', '255,165,0', '2019/08/06', '19:53', '58'),
(6, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/08/07', '15:47', ''),
(7, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/08/08', '07:48', ''),
(8, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/08/09', '11:52', ''),
(9, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/08/11', '13:53', ''),
(10, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/08/12', '11:50', ''),
(11, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/08/13', '14:49', ''),
(12, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/08/14', '14:54', ''),
(13, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/08/14', '17:55', '88'),
(14, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/08/15', '14:55', ''),
(15, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/08/16', '11:01', '102'),
(16, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/08/16', '07:30', ''),
(17, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/08/17', '15:03', ''),
(18, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/08/17', '15:08', '79'),
(19, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/08/18', '15:11', ''),
(20, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/08/18', '10:36', '91'),
(21, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/08/19', '15:51', ''),
(22, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/08/20', '11:02', ''),
(23, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/08/21', '11:05', ''),
(24, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/08/22', '07:50', '110'),
(25, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/08/22', '20:47', ''),
(26, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/08/23', '11:39', '100'),
(27, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/08/23', '11:42', '105'),
(28, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/08/24', '19:21', ''),
(29, 'mus', 'low', 'preset', '01001', '255,165,0', '2019/08/24', '16:28', '57'),
(30, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/08/25', '09:44', ''),
(31, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/08/26', '10:47', '84'),
(32, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/08/26', '10:01', ''),
(33, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/08/27', '08:01', ''),
(34, 'mus', 'office', 'preset', '04002', '150,209,220', '2019/08/28', '16:04', ''),
(35, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/08/28', '09:38', '89'),
(36, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/08/29', '10:08', ''),
(37, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/08/30', '15:13', ''),
(38, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/08/30', '16:33', '106'),
(39, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/08/31', '21:55', ''),
(40, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/09/01', '15:26', ''),
(41, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/09/02', '15:45', '82'),
(42, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/09/02', '16:00', ''),
(43, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/09/03', '13:38', ''),
(44, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/09/04', '18:51', '102'),
(45, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/09/04', '14:25', ''),
(46, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/09/05', '16:03', ''),
(47, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/09/06', '21:34', ''),
(48, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/09/07', '22:18', ''),
(49, 'mus', 'low', 'preset', '01001', '255,165,0', '2019/09/07', '10:58', '58'),
(50, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/09/08', '13:47', ''),
(51, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/09/09', '09:49', ''),
(52, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/09/10', '11:26', '90'),
(53, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/09/10', '09:10', ''),
(54, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/09/11', '13:02', ''),
(55, 'mus', 'relax', 'preset', '05002', '0,120,0', '2019/09/12', '18:19', ''),
(56, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/09/12', '07:05', '82'),
(57, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/09/13', '15:37', '104'),
(58, 'mus', 'relax', 'preset', '05002', '0,120,0', '2019/09/13', '19:51', ''),
(59, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/09/14', '13:10', ''),
(60, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/09/15', '22:43', ''),
(61, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/09/15', '08:26', '77'),
(62, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/09/16', '12:50', '110'),
(63, 'mus', 'morning', 'preset', '06002', '255,255,0', '2019/09/16', '09:39', ''),
(64, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/09/17', '16:53', ''),
(65, 'mus', 'office', 'preset', '04002', '150,209,220', '2019/09/18', '14:55', ''),
(66, 'mus', 'night', 'preset', '07002', '255,204,0', '2019/09/19', '21:57', ''),
(67, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/09/19', '18:57', '92'),
(68, 'mus', 'morning', 'preset', '06002', '255,255,0', '2019/09/20', '08:04', ''),
(69, 'mus', 'relax', 'preset', '05002', '0,120,0', '2019/09/21', '17:16', ''),
(70, 'mus', 'relax', 'preset', '05002', '0,120,0', '2019/09/22', '19:31', ''),
(71, 'mus', 'low', 'preset', '01001', '255,165,0', '2019/09/22', '10:01', '58'),
(72, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/09/23', '09:40', ''),
(73, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/09/24', '12:16', '80'),
(74, 'mus', 'relax', 'preset', '05002', '0,120,0', '2019/09/24', '20:21', ''),
(75, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/09/25', '13:04', ''),
(76, 'mus', 'low', 'preset', '01001', '255,165,0', '2019/09/26', '19:28', '56'),
(77, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/09/26', '23:31', ''),
(78, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/09/27', '09:34', ''),
(79, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/09/28', '09:36', '93'),
(80, 'mus', 'relax', 'preset', '05002', '0,120,0', '2019/09/28', '09:43', ''),
(81, 'mus', 'relax', 'preset', '05002', '0,120,0', '2019/09/29', '09:52', ''),
(82, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/09/29', '12:10', '82'),
(83, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/09/30', '12:44', '90'),
(84, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/09/30', '09:47', ''),
(85, 'mus', 'morning', 'preset', '06002', '255,255,0', '2019/10/01', '09:47', ''),
(86, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/10/02', '22:47', ''),
(87, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/02', '09:47', ''),
(88, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/10/03', '10:02', ''),
(89, 'mus', 'morning', 'preset', '06002', '255,255,0', '2019/10/04', '10:02', ''),
(90, 'mus', 'office', 'preset', '04002', '150,209,220', '2019/10/04', '13:02', ''),
(91, 'mus', 'relax', 'preset', '05002', '0,120,0', '2019/10/05', '10:26', ''),
(92, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/10/05', '10:27', '76'),
(93, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/10/06', '10:28', ''),
(94, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/07', '10:28', ''),
(95, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/08', '10:46', ''),
(96, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/10/08', '11:01', '82'),
(97, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/10/09', '11:01', ''),
(98, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/10/09', '11:24', '104'),
(99, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/10/10', '11:25', ''),
(100, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/10/10', '11:25', '110'),
(101, 'mus', 'low', 'preset', '01001', '255,165,0', '2019/10/11', '16:32', '54'),
(102, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/10/11', '09:21', ''),
(103, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/12', '14:39', ''),
(104, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/10/13', '08:41', ''),
(105, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/13', '16:22', ''),
(106, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/10/13', '20:22', ''),
(107, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/14', '14:11', ''),
(108, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/10/15', '08:13', ''),
(109, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/11/15', '23:57', ''),
(110, 'mus', 'low', 'preset', '01001', '255,165,0', '2019/10/16', '16:37', '58'),
(111, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/16', '19:35', ''),
(112, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/10/17', '15:35', ''),
(113, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/10/18', '06:53', ''),
(114, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/10/18', '19:35', '76'),
(115, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/19', '13:54', ''),
(116, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/10/19', '18:13', '78'),
(117, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/10/20', '07:23', ''),
(118, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/21', '22:16', ''),
(119, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/10/21', '22:23', '84'),
(120, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/10/22', '22:23', ''),
(121, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/23', '22:23', ''),
(122, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/10/24', '22:23', '92'),
(123, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/24', '22:31', ''),
(124, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/10/25', '07:51', ''),
(125, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/10/25', '15:33', '103'),
(126, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/10/26', '16:16', '102'),
(127, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/26', '14:25', ''),
(128, 'mus', 'office', 'preset', '04002', '150,209,220', '2019/10/27', '13:56', ''),
(129, 'mus', 'morning', 'preset', '06002', '255,255,0', '2019/10/28', '07:56', ''),
(130, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/10/28', '17:56', '108'),
(131, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/10/28', '18:17', ''),
(132, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/10/29', '13:25', ''),
(133, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/10/29', '22:54', ''),
(134, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/10/30', '13:54', ''),
(135, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/10/30', '19:54', ''),
(136, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/10/31', '10:47', '90'),
(137, 'mus', 'relax', 'preset', '05002', '0,120,0', '2019/10/31', '16:53', ''),
(138, 'mus', 'night', 'preset', '07002', '255,204,0', '2019/10/31', '22:09', ''),
(139, 'mus', 'morning', 'preset', '06002', '255,255,0', '2019/11/01', '08:35', ''),
(140, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/11/01', '13:07', ''),
(141, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/11/02', '15:09', ''),
(142, 'mus', 'morning', 'preset', '06002', '255,255,0', '2019/11/03', '09:09', ''),
(143, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/03', '14:13', '72'),
(144, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/11/04', '07:13', ''),
(145, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/04', '19:13', '84'),
(146, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/11/05', '07:03', ''),
(147, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/05', '19:31', '82'),
(148, 'mus', 'relax', 'preset', '05003', '0,120,0', '2019/11/06', '18:04', ''),
(149, 'mus', 'low', 'preset', '01001', '255,165,0', '2019/11/06', '19:40', '56'),
(150, 'mus', 'low', 'preset', '01001', '255,165,0', '2019/11/07', '20:19', '59'),
(151, 'mus', 'office', 'preset', '04002', '150,209,220', '2019/11/08', '13:04', ''),
(152, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/11/09', '09:14', ''),
(153, 'mus', 'morning', 'preset', '06002', '255,255,0', '2019/11/10', '08:21', ''),
(154, 'mus', 'night', 'preset', '07002', '255,204,0', '2019/11/11', '00:16', ''),
(155, 'mus', 'night', 'preset', '07002', '255,204,0', '2019/11/11', '00:56', ''),
(156, 'mus', 'night', 'preset', '07003', '255,204,0', '2019/11/12', '00:27', ''),
(157, 'mus', 'relax', 'preset', '05003', '0,120,0', '2019/11/13', '18:18', ''),
(158, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/13', '19:18', '79'),
(159, 'mus', 'relax', 'preset', '05003', '0,120,0', '2019/11/14', '20:45', ''),
(160, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/14', '21:15', '80'),
(161, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/15', '12:13', '98'),
(162, 'mus', 'office', 'preset', '04002', '150,209,220', '2019/11/15', '14:42', ''),
(163, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/11/16', '13:17', ''),
(164, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/16', '14:41', '82'),
(165, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/17', '13:17', '74'),
(166, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/11/17', '20:17', ''),
(167, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/11/18', '08:03', ''),
(168, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/11/18', '23:44', ''),
(169, 'mus', 'office', 'preset', '04002', '150,209,220', '2019/11/19', '14:23', ''),
(170, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/19', '15:17', '96'),
(171, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/11/20', '13:46', ''),
(172, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/20', '14:03', '87'),
(173, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/11/21', '13:33', '110'),
(174, 'mus', 'office', 'preset', '04002', '150,209,220', '2019/11/21', '15:40', ''),
(175, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/11/22', '13:03', '103'),
(176, 'mus', 'morning', 'preset', '06002', '255,255,0', '2019/11/23', '07:05', ''),
(177, 'mus', 'low', 'preset', '01001', '255,165,0', '2019/11/23', '18:05', '57'),
(178, 'mus', 'morning', 'preset', '06002', '255,255,0', '2019/11/24', '08:06', ''),
(179, 'mus', 'night', 'preset', '07002', '255,204,0', '2019/11/24', '22:14', ''),
(180, 'mus', 'relax', 'preset', '05002', '0,120,0', '2019/11/25', '19:31', ''),
(181, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/25', '20:16', '84'),
(182, 'mus', 'morning', 'preset', '06002', '255,255,0', '2019/11/26', '09:18', ''),
(183, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/11/26', '12:48', ''),
(184, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/26', '14:55', '93'),
(185, 'mus', 'night', 'preset', '07002', '255,204,0', '2019/11/26', '23:15', ''),
(186, 'mus', 'morning', 'preset', '06002', '255,255,0', '2019/11/27', '08:07', ''),
(187, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/27', '13:15', '77'),
(188, 'mus', 'office', 'preset', '04002', '150,209,220', '2019/11/27', '17:29', ''),
(189, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/28', '13:11', '81'),
(190, 'mus', 'office', 'preset', '04002', '150,209,220', '2019/11/28', '15:51', ''),
(191, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/29', '13:47', '93'),
(192, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/11/29', '15:04', ''),
(193, 'mus', 'office', 'preset', '04002', '150,209,220', '2019/11/30', '13:01', ''),
(194, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/11/30', '15:02', '76'),
(195, 'mus', 'night', 'preset', '07001', '255,204,0', '2019/11/30', '00:02', ''),
(196, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/12/01', '18:02', ''),
(197, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/12/02', '09:02', ''),
(198, 'mus', 'night', 'preset', '07002', '255,204,0', '2019/12/02', '22:33', ''),
(199, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/12/03', '07:14', ''),
(200, 'mus', 'excitement', 'preset', '03001', '255,115,179', '2019/12/03', '09:52', '122'),
(229, 'mus', 'relax', 'preset', '05003', '0,120,0', '2019/12/03', '09:11', ''),
(230, 'mus', 'office', 'preset', '04002', '150,209,220', '2019/12/03', '09:11', ''),
(231, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/12/03', '13:31', ''),
(232, 'mus', 'calm', 'preset', '02001', '25,195,219', '2019/12/03', '14:09', '83'),
(233, 'mus', 'office', 'preset', '04001', '150,209,220', '2019/12/03', '14:09', ''),
(234, 'mus', 'relax', 'preset', '05001', '0,120,0', '2019/12/03', '14:17', ''),
(235, 'mus', 'morning', 'preset', '06001', '255,255,0', '2019/12/03', '14:17', ''),
(236, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/09', '22:38', ''),
(237, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/09', '22:38', ''),
(238, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/09', '22:38', ''),
(239, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/09', '22:38', ''),
(240, 'mus', 'morning', 'preset', '06001', '255,255,0', '2020/01/09', '22:38', ''),
(241, 'mus', 'night', 'preset', '07001', '255,204,0', '2020/01/09', '22:38', ''),
(242, 'mus', 'morning', 'preset', '06001', '255,255,0', '2020/01/09', '22:38', ''),
(243, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/09', '22:38', ''),
(244, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/09', '22:38', ''),
(245, 'mus', 'office', 'preset', '04001', '150,209,220', '2020/01/09', '22:38', ''),
(246, 'mus', 'office', 'preset', '04001', '150,209,220', '2020/01/09', '22:38', ''),
(247, 'mus', 'morning', 'preset', '06001', '255,255,0', '2020/01/09', '22:38', ''),
(248, 'mus', 'night', 'preset', '07001', '255,204,0', '2020/01/09', '22:38', ''),
(249, 'mus', 'night', 'preset', '07001', '255,204,0', '2020/01/09', '22:38', ''),
(250, 'mus', 'morning', 'preset', '06001', '255,255,0', '2020/01/09', '22:38', ''),
(251, 'mus', 'office', 'preset', '04002', '150,209,220', '2020/01/09', '22:38', ''),
(252, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/09', '22:38', ''),
(253, 'mus', 'relax', 'preset', '05003', '0,120,0', '2020/01/09', '22:38', ''),
(254, 'mus', 'morning', 'preset', '06001', '255,255,0', '2020/01/09', '22:38', ''),
(255, 'mus', 'night', 'preset', '07001', '255,204,0', '2020/01/09', '22:38', ''),
(256, 'mus', 'office', 'preset', '04001', '150,209,220', '2020/01/09', '22:38', ''),
(257, 'mus', 'morning', 'preset', '06001', '255,255,0', '2020/01/09', '22:38', ''),
(258, 'mus', 'night', 'preset', '07001', '255,204,0', '2020/01/09', '22:38', ''),
(259, 'mus', 'office', 'preset', '04001', '150,209,220', '2020/01/09', '22:38', ''),
(260, 'mus', 'office', 'preset', '04001', '150,209,220', '2020/01/09', '22:38', ''),
(261, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/09', '22:38', ''),
(262, 'mus', 'morning', 'preset', '06001', '255,255,0', '2020/01/09', '22:38', ''),
(263, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/09', '22:38', ''),
(264, 'mus', 'night', 'preset', '07001', '255,204,0', '2020/01/09', '22:38', ''),
(265, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/09', '22:38', ''),
(266, 'mus', 'morning', 'preset', '06001', '255,255,0', '2020/01/10', '00:19', ''),
(267, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/10', '00:32', ''),
(268, 'mus', 'morning', 'preset', '06001', '255,255,0', '2020/01/10', '00:32', ''),
(269, 'mus', 'office', 'preset', '04001', '150,209,220', '2020/01/10', '01:22', ''),
(270, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/10', '01:42', ''),
(271, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/10', '01:42', ''),
(272, 'mus', 'morning', 'preset', '06001', '255,255,0', '2020/01/10', '01:42', ''),
(273, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/10', '01:42', ''),
(274, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/10', '01:42', ''),
(275, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/10', '01:42', ''),
(276, 'mus', 'office', 'preset', '04001', '150,209,220', '2020/01/10', '01:42', ''),
(277, 'mus', 'relax', 'preset', '05001', '0,120,0', '2020/01/10', '01:42', ''),
(278, 'mus', 'office', 'preset', '04002', '150,209,220', '2020/01/10', '02:50', '');

-- --------------------------------------------------------

--
-- 資料表結構 `standard`
--

CREATE TABLE `standard` (
  `standard_id` int(10) NOT NULL,
  `mode` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `mode_type` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `music_chiname` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `music_engname` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `music_file` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `light_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `light_rgb` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 傾印資料表的資料 `standard`
--

INSERT INTO `standard` (`standard_id`, `mode`, `mode_type`, `music_chiname`, `music_engname`, `music_file`, `light_name`, `light_rgb`) VALUES
(1, 'low', 'preset', '貝多芬《悲愴奏鳴曲》', 'Beethoven《Pathetique》', '01001', 'Orange', '255,165,0'),
(2, 'calm', 'preset', '柴可夫斯基《花之圓舞曲》', 'Tchaikovsky《Waltz Of The Flowers》', '02001', 'LightBlue', '25,195,219'),
(3, 'excitement', 'preset', '帕海貝爾《D大調卡農與吉格》', 'Pachelbel《Canon and Gigue in D major》', '03001', 'IndianRed', '255,115,179'),
(4, 'office', 'preset', '莫札特《D大調雙鋼琴奏鳴曲》', 'Mozart《Sonata for Two Pianos in D-major》', '04001', 'Fluorescent', '150,209,220'),
(5, 'relax', 'preset', '莫札特《土耳其進行曲》', 'Mozart《Piano Sonata no 11 in A, K 331》', '05001', 'MediumAquamarine', '0,120,0'),
(6, 'morning', 'preset', '葛利格《皮爾金組曲－晨歌》', 'Grieg《Peer Gynt Suite no. 1, Morning Mood》', '06001', 'Yellow', '255,255,0'),
(7, 'night', 'preset', '巴哈《G弦之歌》', 'Bach《Air on the G String》', '07001', 'LightOrange', '255,204,0'),
(8, 'office', 'preset', '莫扎特 《G小調第四十交響曲》', 'Mozart《Symphony no. 40 in G minor》', '04002', 'Fluorescent', '150,209,220'),
(9, 'low', 'preset', '巴伯《弦樂慢板》', 'Jensen《String Quartet, Op. 11 - II》', '01002', 'Orange', '255,165,0'),
(10, 'low', 'preset', '阿爾比諾尼 《G小調慢板》', 'Tomaso 《Adagio in g minor》', '01003', 'Orange', '255,165,0'),
(11, 'low', 'preset', '布拉姆斯《大學慶典序曲》 Op. 80', 'Academic《Festival Overture, Op. 80》', '01004', 'Orange', '255,165,0'),
(12, 'calm', 'preset', '莫雷拉《新年國歌》', 'Quincas Moreira《New Year\'s Anthem》', '02002', 'LightBlue', '25,195,219'),
(13, 'calm', 'preset', 'Spazz Cardigan《 清醒夢》', 'Spazz Cardigan《 Lucid Dreamer》', '02003', 'LightBlue', '25,195,219'),
(14, 'excitement', 'preset', '韋瓦第《四季》-〈春〉第一樂章', 'Vivaldi《The Four Seasons Violin (Spring)》', '03002', 'IndianRed', '255,115,179'),
(15, 'relax', 'preset', '布拉姆斯《第五號匈牙利舞曲》', 'Brahms《Hungarian Dance no. 5》', '05002', 'MediumAquamarine', '0,120,0'),
(16, 'relax', 'preset', '布拉姆斯《第一號交響曲》', 'Brahms《Symphony No. 1 in C Minor》', '05003', 'MediumAquamarine', '0,120,0'),
(17, 'morning', 'preset', '羅西尼《威廉泰爾序曲》', 'Rossini《Overture to William Tell》', '06002', 'Yellow', '255,255,0'),
(18, 'night', 'preset', '羅伯特《夢幻曲》', 'Robert《Dreaming _ Reverie (Träumerei)》', '07002', 'LightOrange', '255,204,0'),
(19, 'night', 'preset', '孟德爾頌《仲夏夜之夢》- 第八首《夜曲》', 'Mendelssohn《A Midsummer Night\'s Dream, Notturno》', '07003', 'LightOrange', '255,204,0');

-- --------------------------------------------------------

--
-- 資料表結構 `user`
--

CREATE TABLE `user` (
  `user_id` int(10) NOT NULL,
  `account` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `gender` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `birthday` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(30) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 傾印資料表的資料 `user`
--

INSERT INTO `user` (`user_id`, `account`, `password`, `name`, `gender`, `birthday`, `email`) VALUES
(1, 'mus', '123', 'music 艾音樂', 'female', '1999/09/09', '105111232@mail.oit.edu.tw'),
(2, 'oit', '321', 'mis804', 'male', '---', 'mis08@mail.com'),
(3, 'mis80408', '3080408', '亞東油條', 'male', '1990/08/23', 'mis804@mail.com'),
(4, 'bee', 'bee', 'bee', 'female', '1995/11/02', 'abcfighting@google.com');

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `heartanalysis`
--
ALTER TABLE `heartanalysis`
  ADD PRIMARY KEY (`analysis_id`);

--
-- 資料表索引 `knowledge`
--
ALTER TABLE `knowledge`
  ADD PRIMARY KEY (`qa_id`);

--
-- 資料表索引 `setting`
--
ALTER TABLE `setting`
  ADD PRIMARY KEY (`setting_id`),
  ADD KEY `useracc_index` (`user_acc`);

--
-- 資料表索引 `standard`
--
ALTER TABLE `standard`
  ADD PRIMARY KEY (`standard_id`),
  ADD KEY `mode_index` (`mode`);

--
-- 資料表索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `acc_index` (`account`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `heartanalysis`
--
ALTER TABLE `heartanalysis`
  MODIFY `analysis_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `knowledge`
--
ALTER TABLE `knowledge`
  MODIFY `qa_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `setting`
--
ALTER TABLE `setting`
  MODIFY `setting_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=279;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `standard`
--
ALTER TABLE `standard`
  MODIFY `standard_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
