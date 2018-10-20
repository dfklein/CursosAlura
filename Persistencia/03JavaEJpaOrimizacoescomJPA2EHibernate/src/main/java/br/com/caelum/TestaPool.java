package br.com.caelum;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TestaPool {

	public static void main(String[] args) {
		try {
			ComboPooledDataSource ds = (ComboPooledDataSource) new JpaConfigurator().getDataSource();

			for(int i=0; i<8; i++) {
				Connection conn = ds.getConnection();
				
				System.out.println(ds.getNumBusyConnections());
				System.out.println(ds.getNumIdleConnections());
			}
		} catch (PropertyVetoException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
