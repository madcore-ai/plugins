pipelineJob('madcore.plugin.spark.submit.jar') {
    parameters {
        stringParam('CLASS', 'graphx.SynthBenchmark', 'Define the class name to use from example. Class name should be relative to: org.apache.spark.examples')
	    stringParam('APP_ARGS', '-app=pagerank -niters=11', 'Input parameters for the example')
	    stringParam('SPARK_ARGS', '--executor-memory 1G', 'Define extra args for spark')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Spark: submit job'
                build job: 'madcore.spark.submit.job', parameters: [string(name: 'APP_FILE_NAME', value: 'spark-examples-1.5.2-hadoop2.6.0.jar'), string(name: 'APP_ARGS', value: params.APP_ARGS), string(name: 'SPARK_ARGS', value: '--class org.apache.spark.examples.' + params.CLASS + ' ' + params.SPARK_ARGS)]
                }
            """.stripIndent())
	    }
    }
}
