package com.nv.baonk.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.nv.baonk.vo.SimpleDepartment;


@Repository("simpleDeptRepository")
public interface SimpleDeptRepository{
	@Query(name = "Test.SimpleDepartment")
	List<SimpleDepartment> getSimpleDeptList(int deptId, int tenantId);
}
