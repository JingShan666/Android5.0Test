package com.example.administrator.android50test.DealWithRyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu on 2016/12/2.
 */

public class PicResource {


    public static List<String> getPic(){
        List mDatas = new ArrayList<String>();
        for (int i = 0; i <30; i++)
        {
            mDatas.add("http://img.woyaogexing.com/2016/11/30/3d7b095fa57d5072!600x600.jpg");
            mDatas.add("http://pic.sc.chinaz.com/files/pic/pic9/201606/apic21503.jpg");
            mDatas.add("http://img.woyaogexing.com/2016/09/21/1dbd144d9708af0d!600x600.jpg");
            mDatas.add("http://pic7.nipic.com/20100428/4635141_094350540830_2.jpg");



        }

        return mDatas;

    }
}
