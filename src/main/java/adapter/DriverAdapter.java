package adapter;

import domain.Driver;
import domain.Ride;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class DriverAdapter extends AbstractTableModel {

    private Driver driver;
    private String[] columnNames = {"From", "To", "Date", "Places", "Price"};
    private List<Ride> rides;

    public DriverAdapter(Driver driver) {
        this.driver = driver;
        this.rides = driver.getRides(); // Driver-en bidai zerrenda eskuratzen dugu
    }

    @Override
    public int getRowCount() {
        return rides.size(); // errenkada kopurua: bidaia kopurua
    }

    @Override
    public int getColumnCount() {
        return columnNames.length; // zutabe kopurua: 5
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ride ride = rides.get(rowIndex);
        switch (columnIndex) {
            case 0: return ride.getFrom();
            case 1: return ride.getTo();
            case 2: return ride.getDate();
            case 3: return ride.getnPlaces();
            case 4: return ride.getPrice();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}