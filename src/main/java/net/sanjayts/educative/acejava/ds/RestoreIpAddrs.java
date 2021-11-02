package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
https://leetcode.com/problems/restore-ip-addresses/
 */
@Slf4j
public class RestoreIpAddrs {

    public static void main(String[] args) {
        String s = "11111111";
        List<String> ips = new RestoreIpAddrs().restoreIpAddresses(s);
        log.info("{} IPs are {}", ips.size(), ips);
    }

    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() < 4 || s.length() > 16) {
            return res;
        }
        LinkedList<String> buf = new LinkedList<>();
        recur(0, s, res, buf, 0);
        return res;
    }

    private void recur(int idx, String s, List<String> res, LinkedList<String> buf, int parts) {
        if (parts == 4) {
            if (idx == s.length()) {
                log.info("SUCCESS recur");
                res.add(String.join(".", buf));
            }
            log.info("FAILED recur with buf as {}", buf);
            return;
        }
        for (int i = 1; i <= 3; ++i) {
            if (idx == s.length() || idx + i > s.length()) {
                continue;
            }
            String part = s.substring(idx, idx + i);
            if (part.length() > 1 && part.startsWith("0")) {
                break;
            }
            if (Integer.parseInt(part) > 255) {
                continue;
            }
            buf.add(part);
            recur(idx + i, s, res, buf, parts + 1);
            buf.removeLast();
        }
    }

}
