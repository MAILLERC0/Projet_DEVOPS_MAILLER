#!groovy
println('------------------------------------------------------------------Import Job CaC/Job3')
def pipelineScript = new File('/var/jenkins_config/jobs/job3-pipeline.groovy').getText("UTF-8")

pipelineJob('CaC/Job3') {
    description("Job Pipline 3 - Ansible")
    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}