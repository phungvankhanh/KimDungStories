package com.example.admiralk.kimdungstories;

import java.util.ArrayList;

/**
 * Created by AdmiralK on 5/7/2017.
 */

public class CheckTuVung {

    char nguyenam[] = {'a', 'á', 'à', 'ạ', 'ả', 'ã', 'ă', 'ắ', 'ằ', 'ặ', 'ẳ', 'ẵ', 'â', 'ấ', 'ầ', 'ẩ', 'ậ', 'ẫ',
            'o', 'ó', 'ò', 'ỏ', 'ọ', 'õ', 'ô', 'ố', 'ồ', 'ổ', 'ộ', 'ỗ',
            'ơ', 'ớ', 'ờ', 'ở', 'ợ', 'ỡ', 'i', 'í', 'ì', 'ỉ', 'ị', 'ĩ',
            'y', 'ỷ', 'ỳ', 'ý', 'ỹ', 'ỵ', 'u', 'ũ', 'ù', 'ú', 'ụ', 'ủ',
            'e', 'é', 'è', 'ẹ', 'ẻ', 'ẽ', 'ế', 'ê', 'ề', 'ể', 'ệ', 'ễ',
            'ư', 'ứ', 'ừ', 'ự', 'ử', 'ữ'
    };

    public int searchString(String key, ArrayList<String> listString){
        //ArrayList<String> arrayString = new ArrayList<>();
        int first = 0;
        int last = listString.size();

        while (first < last){
            int mid = (first + last) /2;
            if (key.compareTo(listString.get(mid)) < 0) {
                last = mid;
            }else if (key.compareTo(listString.get(mid)) > 0){
                first = mid + 1;
            }else
            {
                return mid;
            }
        }
        return -(first + 1);
    }

    public String splitAmGiua(String string) {
        String amGiua = new String();
        int docNguyenAmDauTien = 0;
        int docPhuAmThuHai = 0;
        int check = 0;
        char c;

        for (int i = 0; i < string.length(); i++) {
            check = 0;
            c = string.charAt(i);
            for (int j = 0; j < nguyenam.length; j++) {
                if (c == nguyenam[j] && docPhuAmThuHai == 0) {
                    docNguyenAmDauTien = 1;
                    check = 1;
                    amGiua += c;
                    break;
                }
            }
            if (check == 0 && docNguyenAmDauTien == 1) {
                docPhuAmThuHai = 1;
            }
        }
        amGiua += '\0';
        return amGiua;
    }

    public String splitAmDau(String string) {
        String amDau = new String();
        char c;
        int check = 0;
        //str[0] = str[1] = null;
        for (int i = 0; i < string.length(); i++) {
            c = string.charAt(i);
            for (int j = 0; j < nguyenam.length; j++) {
                if (c == nguyenam[j]) {
                    check = 1;
                    break;
                }
            }
            if (check ==0){
                amDau+=c;
            }
        }
        amDau += '\0';
        return amDau;
    }

    public String splitAmCuoi(String string) {
        String amCuoi= new String();
        char c;
        int check = 0;
        //str[0] = str[1] = null;
        for (int i = string.length() - 1; i >= 0 ; i--) {
            c = string.charAt(i);
            for (int j = 0; j < nguyenam.length; j++) {
                if (c == nguyenam[j]) {
                    check = 1;
                    break;
                }
            }
            if (check ==0){
                amCuoi+=c;
            }
        }
        amCuoi += '\0';
        return amCuoi;
    }
}
