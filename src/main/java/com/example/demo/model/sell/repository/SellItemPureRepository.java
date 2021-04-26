package com.example.demo.model.sell.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.sell.entity.SellItemPureEntity;
import com.example.demo.model.sell.proj.SellItemGroupProj;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SellItemPureRepository extends JpaRepository<SellItemPureEntity, String>{
    /**
     * String getProdNo();
     * String getProdName();
     * String getOptionInfo();
     * int getUnitSum();
     * int getShippingSum();
     * int getAmountSum();
     * @return
     */
    @Query(
        "SELECT si.mallName AS mallName, si.prodNo AS prodNo, MAX(si.prodName) AS prodName, si.optionInfo AS optionInfo, SUM(si.unit) AS unitSum, SUM(si.shipping) AS shippingSum, SUM(si.amount) AS amountSum\n"+
        "FROM SellItemPureEntity si\n"+
        "WHERE si.mallName=:mallName AND si.regDate BETWEEN :startDate AND :endDate\n"+
        "GROUP BY si.mallName, si.prodNo, si.optionInfo"
    )
    List<SellItemGroupProj> selectGroupByProdNoAOptionInfo(String mallName, Date startDate, Date endDate);

    @Query("SELECT si FROM SellItemPureEntity si WHERE (si.regDate BETWEEN :startDate AND :endDate) AND si.mallName=:mallName")
    List<SellItemPureEntity> selectAllByMallADateRange(String mallName, Date startDate, Date endDate);

    @Query("SELECT si FROM SellItemPureEntity si WHERE si.prodOrderNo IN :prodOrderNos")
    List<SellItemPureEntity> findAllByProdOrderNo(List<String> prodOrderNos);

    @Query("SELECT si FROM SellItemPureEntity si WHERE si.prodOrderNo = :prodOrderNo")
    Optional<SellItemPureEntity> findByProdOrderNo(String prodOrderNo);

    @Modifying
    @Query("DELETE FROM SellItemPureEntity si WHERE si.mallName=:mallName AND si.prodOrderNo IN :prodOrderNos")
    int deleteSomeByProdOrderNos(List<String> prodOrderNos, String mallName);
}
