/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.singeev;

import com.lambdaworks.crypto.SCryptUtil;
import org.bouncycastle.crypto.generators.SCrypt;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ScryptBenchmark {

    private static final String PASSWORD = "bestPassword";
    private static final String SALT = "Kru9z58jMOQGjXAUd9wSHxxc3iSjCrkF8eccynd1VzhwRF4FDg";
    private static final byte[] PASSWORD_BYTES = "bestPassword".getBytes();
    private static final byte[] SALT_BYTES = "Kru9z58jMOQGjXAUd9wSHxxc3iSjCrkF8eccynd1VzhwRF4FDg".getBytes();

    @Param({"2", "1024", "8192"})
    public int cpuCostParameter;

    @Setup
    public void setup(BenchmarkParams params) {
    }

    @Benchmark
    @Fork(1)
    public String generateScryptByLambdaworks() {
        return SCryptUtil.scrypt(PASSWORD + SALT, cpuCostParameter, 8, 1);
    }

    @Benchmark
    @Fork(1)
    public byte[] generateScryptByBouncyCastle(){
        return SCrypt.generate(PASSWORD_BYTES, SALT_BYTES, cpuCostParameter, 8, 1, 32);
    }
}
