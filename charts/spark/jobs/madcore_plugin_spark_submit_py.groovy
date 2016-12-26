pipelineJob('madcore.plugin.spark.submit.py') {
    parameters {
        stringParam('PY_FILE', 'pi.py', 'Define the py files from examples.')
	    stringParam('APP_ARGS', '10', 'Input parameters for the example script')
	    stringParam('SPARK_ARGS', '--executor-memory 1G', 'Define extra args for spark')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Spark: submit job'
                build job: 'madcore.spark.submit.job', parameters: [string(name: 'APP_FILE_NAME', value: params.PY_FILE), string(name: 'APP_ARGS', value: params.APP_ARGS), string(name: 'SPARK_ARGS', value: params.SPARK_ARGS)]
                }
            """.stripIndent())
	    }
    }
}
