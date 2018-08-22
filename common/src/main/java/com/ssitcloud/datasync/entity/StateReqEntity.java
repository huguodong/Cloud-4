package com.ssitcloud.datasync.entity;

/**
 * 
 * {
 * 
 * 
 * 
 * }
 * {
    "library_id":"xxx",
    "device_id":"xxx",
    "bin_state": {
        "cardbin": {
            "amount": "99",
            "state": "0"
        },
        "cashbin": [
            {
                "subtype": "50",
                "amount": "2",
                "state": "0"
            },
            {
                "subtype": "100",
                "amount": "5",
                "state": "0"
            }
        ],
        "bookbin": [
            {
                "binno": "1",
                "subtype": "1",
                "amount": "1",
                "state": "0"
            },
            {
                "binno": "2",
                "subtype": "1",
                "amount": "1",
                "state": "0"
            },
            {
                "binno": "3",
                "subtype": "1",
                "amount": "1",
                "state": "0"
            },
            {
                "binno": "4",
                "subtype": "2",
                "amount": "1",
                "state": "0"
            },
            {
                "binno": "5",
                "subtype": "10",
                "amount": "1",
                "state": "0"
            }
        ]
    }
}

 * @package: com.ssitcloud.datasync.entity
 * @classFile: BinStateReqEntity
 * @author: liuBh
 * @description: TODO
 */
public class StateReqEntity {
	private String library_id;
	private String device_id;
	private String table;
	
	
}
