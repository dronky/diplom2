package com.nadezda.diplom.tables;
import com.sun.javafx.scene.control.skin.TableViewSkin;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Skin;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//Magic class for resize table columns, when clicking on it.
public class ViewUtility {

        private static Method columnToFitMethod;
        static {
            try {
                columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
                columnToFitMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        public static void autoFitTable(TableView tableView) {
            tableView.getItems().addListener(new ListChangeListener<Object>() {
                @Override
                public void onChanged(ListChangeListener.Change<?> c) {
                    for (Object column : tableView.getColumns()) {
                        try {
                            columnToFitMethod.invoke(tableView.getSkin(), column, -1);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }


}
