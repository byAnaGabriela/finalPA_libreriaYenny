package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class RepositoryBase<T> {

    protected static final Connection connection = Conexion.getInstance().getConnection();
    protected abstract T mapear(ResultSet rs) throws SQLException;

}
