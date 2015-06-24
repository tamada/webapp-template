package com.github.sckit;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import winstone.Launcher;

public class Main{
    private int port = 8888;

    public Main(String[] args) throws Exception{
        // war 自身の場所を取得
        URL warLocation = Main.class.getProtectionDomain().getCodeSource().getLocation();

        // コマンドライン引数ほぼそのまま winstone に渡す。
        // war ファイルの場所だけ加える。
        List<String> argList = new ArrayList<String>(Arrays.asList(args));
        if(!checkAndGetPort(argList)){
            argList.add("--httpPort=8888");
        }
        argList.add("--useJasper");
        argList.add("--warfile=" + warLocation.getPath());

        Launcher.main(argList.toArray(new String[argList.size()]));

        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI("http://localhost:" + port));
        }
    }

    private boolean checkAndGetPort(List<String> list){
        String starts = "--httpPort=";
        for(String item: list){
            if(item.startsWith(starts)){
                this.port = Integer.parseInt(item.substring(starts.length()));
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception{
        new Main(args);
    }
}