package com.lee.drag

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Logo(val text: String, val url: String)

const val JSON = """[
    {
        "text": "TEMEET",
        "url": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598542716001&di=9d811219a527410b119e25d19b27cf38&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Forj480%2F5d3745a5gw1f32kr1sblxj211f11ftb3.jpg"
    },
    {
        "text": "YOU NAME",
        "url": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598542716000&di=386d358ee3d75b91903ff884ed3d1e63&imgtype=0&src=http%3A%2F%2Fsc.yymoban.com%2FUploadFiles%2FExample%2F2020%2F03%2F24%2FY5uR1585038648.png"
    },
    {
        "text": "8-MARK",
        "url": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598542716000&di=08ddcf5a382fe6bb01653cc842d7865f&imgtype=0&src=http%3A%2F%2Fdn-kdt-img.qbox.me%2Fupload_files%2F2015%2F05%2F15%2FFtZUXKNvRcLAKOPUNymO7J1bnU9Z.jpg"
    },
    {
        "text": "GA",
        "url": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598542716000&di=4e43c5dae12dedf45fdb1c6106839f99&imgtype=0&src=http%3A%2F%2Fimg.959.cn%2F7%2F2011%2F1103%2F20111103104318810.jpg"
    },
    {
        "text": "维库",
        "url": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598542832676&di=18c53171f3f471420008f0d8d6a3ea3b&imgtype=0&src=http%3A%2F%2Fpic3.zhimg.com%2Fv2-73c92834efe3b0e13c662f79b2eea8df_xs.jpg%3Fsource%3D172ae18b"
    },
    {
        "text": "REMAX",
        "url": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598542864532&di=ee12c4fe4b2ca7a593bd384ac39ed4c0&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D2923777027%2C1839383948%26fm%3D214%26gp%3D0.jpg"
    },
    {
        "text": "古茗",
        "url": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598542832674&di=d175c62a3d2dcb91e3740ed1653ae83a&imgtype=0&src=http%3A%2F%2Fimg.res.jmbbs.com%2Fattachment%2Fforum%2F201908%2F01%2F165409sz00j23y026wpyy0.jpg"
    },
    {
        "text": "1937",
        "url": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598542832672&di=e41159c29af9eba65b607c3167ab0a56&imgtype=0&src=http%3A%2F%2Fimage.thepaper.cn%2Fwww%2Fimage%2F48%2F830%2F910.jpg"
    },
    {
        "text": "苏宁易购",
        "url": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598542918077&di=d1ff03d18469795a4c8768b8bef3620d&imgtype=0&src=http%3A%2F%2Fa.zdmimg.com%2F201504%2F23%2F5538370dc7851720.png_d480.jpg"
    },
    {
        "text": "名师课堂",
        "url": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598542918072&di=21c61f98add35b39948ab41dd7e03226&imgtype=0&src=http%3A%2F%2Fhiphotos.baidu.com%2Fdoc%2Fpic%2Fitem%2Fd6ca7bcb0a46f21f14cde428fd246b600d33aed4.jpg"
    }
]"""

suspend fun getLogoData(): MutableList<Logo> = withContext(Dispatchers.IO) {
    Gson().fromJson(JSON, object : TypeToken<List<Logo>>() {}.type)
}