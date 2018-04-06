package vn.com.hieptt.myhiittimer.selectcolor;

/**
 * Created by Admin on 1/17/2018.
 */

public class ItemRvSelectColor {
    private String titleColor;
    private int imgColor,imgCheck;
    private boolean isSelected;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ItemRvSelectColor(String titleColor, int imgColor, int imgCheck, boolean isSelected) {
        this.titleColor = titleColor;
        this.imgColor = imgColor;
        this.imgCheck = imgCheck;
        this.isSelected = isSelected;
    }


    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public int getImgColor() {
        return imgColor;
    }

    public void setImgColor(int imgColor) {
        this.imgColor = imgColor;
    }

    public int getImgCheck() {
        return imgCheck;
    }

    public void setImgCheck(int imgCheck) {
        this.imgCheck = imgCheck;
    }



}
